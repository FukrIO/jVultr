import com.vacrodex.jvultr.jVultr;

/**
 * @author Cameron Wolfe
 */
public class Main {
  
  public static void main(String[] args) throws Exception {
    
    jVultr api = new jVultr(System.getenv("vultr.token"));
    
    
  }
}
