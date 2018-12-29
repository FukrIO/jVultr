package com.vacrodex.jvultr.utils.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.vacrodex.jvultr.exceptions.InvalidEndpointException;
import com.vacrodex.jvultr.exceptions.InvalidHttpMethodException;
import com.vacrodex.jvultr.exceptions.InvalidKeyException;
import com.vacrodex.jvultr.exceptions.MoreDetailedException;
import com.vacrodex.jvultr.exceptions.RateLimitedException;
import com.vacrodex.jvultr.jVultr;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Cameron Wolfe
 */
@Slf4j
public class RestRequest<T> {
  
  private final jVultr api;
  private final RestMethods method;
  private final RestEndpoints endpoint;
  private final CompletableFuture<RestRequestResult> result = new CompletableFuture<>();
  /**
   * The origin of the rest request.
   */
  private final Exception origin;
  private int ratelimitRetries = 1000;
  private String[] urlParameters = new String[0];
  private List<String[]> queryParameters = new ArrayList<>();
  private String body = null;
  private int retryCounter = 0;
  /**
   * The multipart body of the request.
   */
  private MultipartBody multipartBody;
  
  /**
   * Creates a new instance of this class.
   *
   * @param api The api which will be used to execute the request.
   * @param method The http method of the request.
   * @param endpoint The endpoint to which the request should be sent.
   */
  public RestRequest(jVultr api, RestMethods method, RestEndpoints endpoint) {
    this.api = api;
    this.method = method;
    this.endpoint = endpoint;
    
    this.origin = new Exception();
  }
  
  /**
   * Gets the api which is used for this request.
   *
   * @return The api which is used for this request.
   */
  public jVultr getApi() {
    return api;
  }
  
  /**
   * Gets the method of this request.
   *
   * @return The method of this request.
   */
  public RestMethods getMethod() {
    return method;
  }
  
  /**
   * Gets the endpoint of this request.
   *
   * @return The endpoint of this request.
   */
  public RestEndpoints getEndpoint() {
    return endpoint;
  }
  
  /**
   * Gets an array with all used url parameters.
   *
   * @return An array with all used url parameters.
   */
  public String[] getUrlParameters() {
    return urlParameters;
  }
  
  /**
   * Sets the url parameters, e.g. a channel getId.
   *
   * @param parameters The parameters.
   *
   * @return The current instance in order to chain call methods.
   */
  public RestRequest<T> setUrlParameters(String... parameters) {
    this.urlParameters = parameters;
    return this;
  }
  
  /**
   * Gets the body of this request.
   *
   * @return The body of this request.
   */
  public Optional<String> getBody() {
    return Optional.ofNullable(body);
  }
  
  /**
   * Sets the body of the request.
   *
   * @param body The body of the request.
   *
   * @return The current instance in order to chain call methods.
   */
  public RestRequest<T> setBody(JsonNode body) {
    return setBody(body.toString());
  }
  
  /**
   * Sets the body of the request.
   *
   * @param body The body of the request.
   *
   * @return The current instance in order to chain call methods.
   */
  public RestRequest<T> setBody(String body) {
    this.body = body;
    return this;
  }
  
  /**
   * Gets the origin of the rest request.
   *
   * @return The origin of the rest request.
   */
  public Exception getOrigin() {
    return origin;
  }
  
  /**
   * Adds a query parameter to the url.
   *
   * @param key The key of the parameter.
   * @param value The value of the parameter.
   *
   * @return The current instance in order to chain call methods.
   */
  public RestRequest<T> addQueryParameter(String key, String value) {
    this.queryParameters.add(new String[]{key, value});
    return this;
  }
  
  /**
   * Sets the multipart body of the request. If a multipart body is set, the {@link
   * #setBody(String)} method is ignored!
   *
   * @param multipartBody The multipart body of the request.
   *
   * @return The current instance in order to chain call methods.
   */
  public RestRequest<T> setMultipartBody(MultipartBody multipartBody) {
    this.multipartBody = multipartBody;
    return this;
  }
  
  /**
   * Sets the amount of ratelimit retries we should use with this request.
   *
   * @param retries The amount of ratelimit retries.
   *
   * @return The current instance in order to chain call methods.
   */
  public RestRequest<T> setRatelimitRetries(int retries) {
    if (retries < 0) {
      throw new IllegalArgumentException("Retries cannot be less than 0!");
    }
    this.ratelimitRetries = retries;
    return this;
  }
  
  /**
   * Increments the amounts of ratelimit retries.
   *
   * @return <code>true</code> if the maximum ratelimit retries were exceeded.
   */
  public boolean incrementRetryCounter() {
    return ++retryCounter > ratelimitRetries;
  }
  
  /**
   * Executes the request. This will automatically retry if we hit a ratelimit.
   *
   * @param function A function which processes the rest response to the requested object.
   *
   * @return A future which will contain the output of the function.
   */
  public CompletableFuture<T> execute(Function<RestRequestResult, T> function) {
    api.getRatelimitManager().queueRequest(this);
    CompletableFuture<T> future = new CompletableFuture<>();
    result.whenComplete((result, throwable) -> {
      if (throwable != null) {
        future.completeExceptionally(throwable);
        return;
      }
      try {
        future.complete(function.apply(result));
      } catch (Throwable t) {
        future.completeExceptionally(t);
      }
    });
    return future;
  }
  
  /**
   * Gets the result of this request. This will not start executing, just return the result!
   *
   * @return Gets the result of this request.
   */
  public CompletableFuture<RestRequestResult> getResult() {
    return result;
  }
  
  /**
   * Executes the request blocking.
   *
   * @return The result of the request.
   *
   * @throws Exception If something went wrong while executing the request.
   */
  public RestRequestResult executeBlocking() throws Exception {
    Request.Builder requestBuilder = new Request.Builder();
    HttpUrl.Builder httpUrlBuilder = endpoint.getHttpUrl(urlParameters).newBuilder();
    for (String[] queryParameter : queryParameters) {
      httpUrlBuilder.addQueryParameter(queryParameter[0], queryParameter[1]);
    }
    requestBuilder.url(httpUrlBuilder.build());
    
    RequestBody requestBody;
    if (multipartBody != null) {
      requestBody = multipartBody;
    } else if (body != null) {
      requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), body);
    } else {
      requestBody = RequestBody.create(null, new byte[0]);
    }
    
    switch (method) {
      case GET:
        requestBuilder.get();
        break;
      case POST:
        requestBuilder.post(requestBody);
        break;
      default:
        throw new IllegalArgumentException("Unsupported http method!");
    }
    log.debug("Trying to send {} request to {}{}", method.name(), endpoint
        .getFullUrl(urlParameters), body != null ? " with body " + body : "");
    
    try (Response response = getApi().getHttpClient().newCall(requestBuilder.build()).execute()) {
      RestRequestResult result = new RestRequestResult(this, response);
      log.debug("Sent {} request to {} and received status code {} with{} body{}", method
          .name(), endpoint.getFullUrl(urlParameters), response.code(), result
          .getBody()
          .map(b -> "")
          .orElse(" empty"), result.getStringBody().map(s -> " " + s).orElse(""));
      
      switch (response.code()) {
        case 400:
          throw new InvalidEndpointException();
        case 403:
          throw new InvalidKeyException();
        case 405:
          throw new InvalidHttpMethodException();
        case 412:
          throw new MoreDetailedException(result.getStringBody().orElse(null));
        case 503:
          throw new RateLimitedException(this);
      }
      return result;
    }
  }
  
}