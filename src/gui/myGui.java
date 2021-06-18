package gui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;


public class myGui {
	
	public static String processString(String e) {
		
		String body = e;
		
		body = body.replaceAll("[\r\n]+", "");
		
		String parts[] = body.split(" ");
		
		String newParts[] = new String[4];
		int j = 0;
		
		for (int i = 0; i < parts.length; i++) {
			if (!parts[i].contains("=>") && parts[i] != ""  && !parts[i].contains("Array(")) {
				newParts[j] = parts[i];
				newParts[j].trim();
				j++;
			}
		}
		return Arrays.toString(newParts);
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
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
		.thenApply(HttpResponse::body)
		.thenApply(myGui::processString)
		.thenAccept(System.out::println)
		.join();
	}
}
