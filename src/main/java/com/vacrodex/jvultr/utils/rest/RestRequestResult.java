package com.vacrodex.jvultr.utils.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.vacrodex.jvultr.exceptions.InvalidEndpointException;
import com.vacrodex.jvultr.exceptions.InvalidKeyException;
import com.vacrodex.jvultr.exceptions.InvalidSubscriptionId;
import com.vacrodex.jvultr.exceptions.MoreDetailedException;
import com.vacrodex.jvultr.exceptions.TooSoonException;
import java.io.IOException;
import java.util.Optional;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Bastian Oppermann
 * @author Cameron Wolfe
 */
public class RestRequestResult {
  
  private final RestRequest<?> request;
  private final Response response;
  private final ResponseBody body;
  private final JsonNode jsonBody;
  private String stringBody;
  
  /**
   * Creates a new RestRequestResult.
   *
   * @param request The request of the result.
   * @param response The response of the RestRequest.
   * @throws IOException Passed on from {@link ResponseBody#string()}.
   */
  public RestRequestResult(RestRequest<?> request, Response response) throws IOException, InvalidEndpointException, InvalidKeyException, MoreDetailedException, InvalidSubscriptionId, TooSoonException {
    this.request = request;
    this.response = response;
    this.body = response.body();
    if (body == null) {
      stringBody = null;
      jsonBody = NullNode.getInstance();
    } else {
      stringBody = body.string();
      ObjectMapper mapper = request.getApi().getObjectMapper();
      if (stringBody.equals("Invalid API endpoint")) {
        throw new InvalidEndpointException();
      }
      if (stringBody.equals("Invalid API key")) {
        throw new InvalidKeyException();
      }
      
      if (stringBody.equals("Invalid server.") || stringBody
          .equals("Invalid server. Check SUBID value and ensure your API key matches the server's account")) {
        throw new InvalidSubscriptionId();
      }
      
      if (stringBody.equals("Unable to terminate server: Servers cannot be destroyed within 5 minutes of being created.")) {
        throw new TooSoonException();
      }
      
      if ("".equals(stringBody)) {
        jsonBody = mapper.readTree("{}");
      } else {
        jsonBody = mapper.readTree(stringBody);
      }
    }
  }
  
  /**
   * Gets the {@link RestRequest} which belongs to this result.
   *
   * @return The ResuRequest which belongs to this result.
   */
  public RestRequest<?> getRequest() {
    return request;
  }
  
  /**
   * Gets the response of the {@link RestRequest}.
   *
   * @return The response of the RestRequest.
   */
  public Response getResponse() {
    return response;
  }
  
  /**
   * Gets the body of the response.
   *
   * @return The body of the response.
   */
  public Optional<ResponseBody> getBody() {
    return Optional.ofNullable(body);
  }
  
  /**
   * Gets the string body of the response.
   *
   * @return The string body of the response.
   */
  public Optional<String> getStringBody() {
    return Optional.ofNullable(stringBody);
  }
  
  /**
   * Gets the json body of the response. Returns a {@link NullNode} if the response had no body.
   *
   * @return The json body of the response.
   */
  public JsonNode getJsonBody() {
    return jsonBody;
  }
}