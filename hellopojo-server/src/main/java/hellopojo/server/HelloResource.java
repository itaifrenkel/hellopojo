package hellopojo.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import hellopojo.model.Hello;

@Path("/")
public class HelloResource {
 
	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	public Hello getHello() {
 
		Hello hello = new Hello();
		hello.setMessage("hello pojo");
 
		return hello;
 
	}
}
