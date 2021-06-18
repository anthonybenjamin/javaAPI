package gui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;

//URLs to remember for this project: 
//https://openjdk.java.net/groups/net/httpclient/recipes.html
//https://www.baeldung.com/jackson-deserialization
public class myGui {
	
	public static String processJson(String r) throws JsonMappingException, JsonProcessingException {
		
		object itemWithOwner = new ObjectMapper().readValue(r, object.class);
		
		return itemWithOwner.body;
	}
	
	public static void main(String args[]) throws IOException, InterruptedException{
		
		var  values = new HashMap<String, String>() {{
			put("title", "Anthony");
			put("body", "Benjamin");
		}};
		
		var objectMapper = new ObjectMapper();	
		String requestBody = objectMapper.writeValueAsString(values);
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://127.0.0.1/java-api-test/"))
				.header("Accept", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
		.thenApply(HttpResponse::body)
		.thenApply(t -> {
			try {
				return processJson(t);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return t;
		})
		.thenAccept(System.out::println)
		.join();
	}
}
