package com.stitchlite.util;

import java.net.URI;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;

public class HttpClientUtil {
	
 public HttpResponse httpGetRequest(String url,Map<String,String> headers) {
	 try {
		 
		    HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			for(String key: headers.keySet()) {
				request.addHeader(key, headers.get(key));
			}
	     	HttpResponse response = client.execute(request);
	     	return response;
		 
	 } catch(Exception e) {
		 
	 }
	 
	return null;	 
}
	
 //oauth2 function to get access token 

}
