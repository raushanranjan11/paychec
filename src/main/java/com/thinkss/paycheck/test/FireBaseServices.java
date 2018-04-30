package com.thinkss.paycheck.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.JSONException;
import org.json.simple.JSONObject;

import com.google.gson.JsonObject;


 
 
 

public class FireBaseServices {
	
		public final static String AUTH_KEY_FCM = "AIzaSyCJNlbJ9eQ0JNC13iM7dWl2G0OX5mUKuqA";
				
		public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
		
		//-----------------------------------working for notification message------------------------------------
		
		public static String sendFCMNotification(String message) {
			System.out.println("FCM    connection");
			
			//JsonObject jsonObject = new JsonObject(message);
	        System.out.println("message : "+message);
			String authKey = "key="+AUTH_KEY_FCM;   // You FCM AUTH key
			String FMCurl = API_URL_FCM;  
			HttpURLConnection urlConnection = null;
			 StringBuilder sb = new StringBuilder();
		    try {
		    	
		    	URL url = new URL(FMCurl);
		        urlConnection = (HttpURLConnection) url.openConnection();
		        urlConnection.setDoOutput(true);
		        urlConnection.setRequestMethod("POST");
		        urlConnection.setUseCaches(false);
		        urlConnection.setConnectTimeout(50000);
		        urlConnection.setReadTimeout(50000);
		        urlConnection.setRequestProperty("Content-Type", "application/json");
		        urlConnection.setRequestProperty("Authorization",authKey);
		        urlConnection.connect();
		        //You Can also Create JSONObject here 
		        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
		        out.write(message.toString());// here i sent the parameter
		        out.close();
		        int HttpResult = urlConnection.getResponseCode();
		        if (HttpResult == HttpURLConnection.HTTP_OK) {
		            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
		            String line = null;
		            while ((line = br.readLine()) != null) {
		                sb.append(line + "\n");
		            }
		            br.close();
			        System.out.println("Builder : " +  sb.toString());
		            return sb.toString();
		        } else {
			        System.out.println("Response Code : " + urlConnection.getResponseMessage());
		        }
		    } catch (MalformedURLException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        if (urlConnection != null)
		            urlConnection.disconnect();
		    }
		    return null;
		}
			
		public static void main(String []args) throws Exception{
			String serverKey = "AIzaSyCJNlbJ9eQ0JNC13iM7dWl2G0OX5mUKuqA";
			String authKey = AUTH_KEY_FCM;   // You FCM AUTH key
			String FMCurl = API_URL_FCM; 
			
			String userDeviceIdKey ="cNMRtvlBpgI:APA91bHo5XfGrvTu4H9RV1Im8YnksnBxA4Pn_iYLrAPnnlyrm-kIhiemu1LbQyzDuOjss9cgRQ_0b7gIpX0bvIhtP3WXLI2VujfU-ObSC0TiT_V5AMbIRbfFYsyE-6hS3MupeF1CBHUp";
			 String iosKey = "0c196d25b801ce7a4e7c3b4ef937106315fb9e5e9157feb94b990bd243f2e4c2";
			List<String> deviceList = new ArrayList<String>();
			deviceList.add(userDeviceIdKey);
//			deviceList.add(iosKey);
			JSONObject info = new JSONObject();
			info.put("title", "Notificatoin Title");   
			info.put("body", "Hello Test notification"); 
//			json.put("data", info);
			
			org.json.simple.JSONObject parentObject = new org.json.simple.JSONObject();
			parentObject.put("data", info);
			parentObject.put("registration_ids", deviceList);
			
			
			FireBaseServices.sendFCMNotification(parentObject.toString());
	
			
			 
			
		}
		
		
 
	
}
