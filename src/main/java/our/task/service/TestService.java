package our.task.service;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
 
@Path("/test-service/")
public interface TestService {
 
    @GET
    @Path("/test")
    public String execute();
}