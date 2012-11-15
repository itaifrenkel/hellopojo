package hellopojo.test;

import hellopojo.client.HelloClient;
import hellopojo.model.Hello;
import hellopojo.server.HelloResource;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class HelloTest {

	private static Server server;
	private static HelloClient client;
	private static String restUri;

	@Parameters({"port"})
	@BeforeClass
	public static void startWebapp(
		@Optional("8081") int port) throws Exception {
	
	  restUri = "http://localhost:"+port+"/rest";
	  client = new HelloClient(new URI(restUri));
	  
	  server = new Server(port);
	  server.setHandler(createWebAppContext());
	  server.start();
	}

	private static ServletContextHandler createWebAppContext() {
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
		ServletHolder servlet = new ServletHolder(new ServletContainer());
		servlet.setInitParameter("com.sun.jersey.config.property.packages", HelloResource.class.getPackage().getName());
		servlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature" ,"true");
		handler.addServlet(servlet, "/rest/*");
		return handler;
	}

	@AfterClass
	public static void stopWebapp() throws Exception {
	  server.stop();
	}
	
	@Test
	public void hello() {
		Hello hello = client.getHello();
		Assert.assertNotNull(hello);
		Assert.assertEquals(hello.getMessage(),"hello pojo");
	}
	
	@Test
	public void helloNotFound() throws URISyntaxException {
		HelloClient blahClient = new HelloClient(new URI(restUri + "blah"));
		Hello hello = blahClient.getHello();
		Assert.assertNull(hello);
	}
}