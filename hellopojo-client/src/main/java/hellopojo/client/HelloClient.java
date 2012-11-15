package hellopojo.client;

import hellopojo.model.Hello;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class HelloClient {
	
	private final Client client;
	private final WebResource webResource;
	
	public HelloClient(URI restUri) {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		client = Client.create(clientConfig);
		webResource = client.resource(restUri);
	}
	
	public Hello getHello() {
		try { 
			return 
				webResource
				.path("hello")
				.accept(MediaType.APPLICATION_JSON)
				.get(Hello.class);
		}
		catch (UniformInterfaceException e) {
			if (e.getResponse().getStatus() == ClientResponse.Status.NOT_FOUND.getStatusCode()) {
				return null;
			}
			throw e;
		}
	}
}
