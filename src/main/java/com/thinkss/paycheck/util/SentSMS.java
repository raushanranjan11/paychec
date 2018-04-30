package com.thinkss.paycheck.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SentSMS {
	
	public final static String AUTH_KEY_FCM = "AIzaSyC8fuuwvrkgIq9kMcJLyrjzAVr-FJHF3xM";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	
	 // Find your Account Sid and Token at twilio.com/user/account
	  public static final String ACCOUNT_SID = "ACebfebc1a1925ff1a822801e0562c6fbf";
	  public static final String AUTH_TOKEN = "0289d0923809d8e48f4fda5c2e46e511";

	public static void main(String[] args) throws JSONException {
		/*Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	    Message message = Message.creator(new PhoneNumber("+918618042612"),
	        new PhoneNumber("+16072142644 "), 
	        "Hi,Raushan /n Your OTP is").create();
	    System.out.println(message.getSid());*/
	 
	sendSms();
//	sendSms1();
	}

	
	public static String sendSms() {
		try {
			// Construct data
			String apiKey = "apikey=" + "0289d0923809d8e48f4fda5c2e46e511";
			String message = "&message=" + "This is your message";
			String sender = "&sender=" + "TXTLCL";
			String numbers = "&numbers=" + "+918618042612";
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}
	
	public static String sendSms1() {
		try {
			// Construct data
			String apiKey = "apikey=" + URLEncoder.encode("0289d0923809d8e48f4fda5c2e46e511", "UTF-8");
			String message = "&message=" + URLEncoder.encode("This is your message", "UTF-8");
			String sender = "&sender=" + URLEncoder.encode("TXTLCL", "UTF-8");
			String numbers = "&numbers=" + URLEncoder.encode("+918618042612", "UTF-8");
			
			// Send data
			String data = "https://api.textlocal.in/send/?" + apiKey + numbers + message + sender;
			URL url = new URL(data);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			String sResult="";
			while ((line = rd.readLine()) != null) {
			// Process line...
				sResult=sResult+line+" ";
			}
			rd.close();
			
			return sResult;
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}
	
}
