package com.thinkss.paycheck.test;


import java.io.OutputStreamWriter;
/*import java.net.*;

import javax.mail.internet.MimeMessage;*/

import javax.mail.internet.MimeMessage;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendMessage {
	
	
	@Autowired
	private JavaMailSender sender;

	public final static String AUTH_KEY_FCM = "AIzaSyC8fuuwvrkgIq9kMcJLyrjzAVr-FJHF3xM";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	
	public static void main(String []args) throws Exception{
	/**	String authKey = AUTH_KEY_FCM;   // You FCM AUTH key
		String FMCurl = API_URL_FCM;    
		String userDeviceIdKey = "fx1SS2Rj9eg:APA91bFvcCyYcAhPGm5eslXxuRzA09W6V7f0leAM_-3I2Uxs8oJeDT-pi2QB92JcUS0zyqYZyC34y-Ud-m443GLX55pKVv4v7j-p3aSzXOR_0qkB7sKD35Eu0NYgsSRUOdRc5sT_zHS-";
		String userDeviceIdKey1 = "djiKeT3XjAE:APA91bGTgIWOagR1VggEdUmGtiJdz-RVcUIoTCtVgkzzc7EHu7tSiO0s5Wz2H2-ViESkiNy8fFR5aXC-sFACBWAtGAih9rYEVHyDiYOIceY5TK87THBbP_E66pFb853xQk5tKvHnjeD9";
	
		URL url = new URL(FMCurl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization","key="+authKey);
		conn.setRequestProperty("Content-Type","application/json");
		JSONObject json = new JSONObject();

		json.put("registration_ids",userDeviceIdKey);
		JSONObject info = new JSONObject();
		info.put("title", "Notificatoin Title");   
		info.put("body", "Hello Test notification"); 
		json.put("data", info);

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString());
		wr.flush();
		 conn.getInputStream();
 */

		SendMessage sm = new SendMessage();
		sm.sendEmail();
	}
	
		
		private void sendEmail() throws Exception{
			
			       	MimeMessage message = sender.createMimeMessage();
			        MimeMessageHelper helper = new MimeMessageHelper(message);
			        helper.setTo("raushanranjan85@gmail.com");
			        helper.setText("How are you?");
			        helper.setSubject("Hi");
			
			

	}
	
		

}
