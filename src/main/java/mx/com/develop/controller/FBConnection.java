/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.develop.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;
import java.net.URLEncoder; 
import javax.ejb.Stateless;  

//Author  SALVADOR ESTRADA
@Stateless
public class FBConnection {
    public static final String FB_APP_ID = "ClientId";
    public static final String FB_APP_SECRET = "ClaveSecreta";
    public static final String REDIRECT_URI = "http://localhost:8080/OAuth2v1/webresources/fbcallback";
    String accessToken = "";
    String stringToken="";

	public String getFBAuthUrl() {
		String fbLoginUrl = "";
		try {
			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
					+ FBConnection.FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(FBConnection.REDIRECT_URI, "UTF-8")
					+ "&scope=email";
                        System.out.println(""+fbLoginUrl);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbLoginUrl;
	}

	public String getFBGraphUrl(String code) {
		String fbGraphUrl = "";
		try {
			fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
					+ "client_id=" + FBConnection.FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(FBConnection.REDIRECT_URI, "UTF-8")
					+ "&client_secret=" + FB_APP_SECRET + "&code=" + code;
                        
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbGraphUrl;
	}
        //Enviar el codigo a facebook para obtener la respuesta(perfil de uduario)
	public String getAccess(String code) {
		if ("".equals(accessToken)) {
			URL fbGraphURL;
			try {
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			} 
                        //Facebook connection
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(fbConnection.getInputStream()));
				String inputLine;
                                b = new StringBuffer();
				while ((inputLine = in.readLine()) != null) 
					b.append(inputLine + "\n");
                                        System.out.println("karina"+b);
				in.close(); 
                                
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook "
						+ e.getMessage());
			}

			accessToken = b.toString();  
                        
                        JSONObject objJava = new JSONObject(accessToken);
                        
                        stringToken = objJava.getString("access_token");
                        
                        
			if (stringToken.startsWith("{")) {
				throw new RuntimeException("ERROR: Access Token Invalid: "
						+ stringToken);
			}
		}
		return stringToken;
	}
}



