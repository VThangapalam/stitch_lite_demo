package com.stitchlite.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
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
 
 public String getAccessTokenFromRefreshToken(String url,String refreshToken,String clientId,String clienSecret) {
	try {
	 HttpClient client = HttpClientBuilder.create().build();
	 
	   HttpPost httpPost = new HttpPost(url);
	    httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    
	    params.add(new BasicNameValuePair("refresh_token", refreshToken));
	    params.add(new BasicNameValuePair("client_id", clientId));
	    params.add(new BasicNameValuePair("client_secret", clienSecret));
	    params.add(new BasicNameValuePair("grant_type", "refresh_token"));
	 
	    httpPost.setEntity(new UrlEncodedFormEntity(params));
	    HttpResponse response = client.execute(httpPost);
	    String result = EntityUtils.toString(response.getEntity());
	    JSONObject resObj = new JSONObject(result);	   
     	return (String) resObj.get("access_token");
	}catch(Exception e) {
		
	}
	return null;
	
 }

}
