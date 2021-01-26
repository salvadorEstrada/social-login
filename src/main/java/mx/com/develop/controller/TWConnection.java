/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.develop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author ESTRADA
 */ 
@WebServlet(name = "TWConnection", urlPatterns = {"/signin"})
public class TWConnection extends HttpServlet{  
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
                ServletOutputStream out = response.getOutputStream(); 
                
        	// configure twitter api with consumer key and secret key
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("ClientId")
		  .setOAuthConsumerSecret("SecretId");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		request.getSession().setAttribute("twitter", twitter); 
                out.println("Hola dede el servlet"); 
                try
                {
                // setup callback URL
                StringBuffer callbackURL = request.getRequestURL();
                int index = callbackURL.lastIndexOf("/"); 
                out.println("Index: "+callbackURL);
                out.println("Index: "+index); 
                out.println(callbackURL.length());
                //callbackURL.replace(45, 52, "");
                callbackURL.replace(index, callbackURL.length(), "").append("/webresources/twitter/callback");
                
                // get request object and save to session
                RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
                request.getSession().setAttribute("requestToken", requestToken);
                
                // redirect to twitter authentication URL
                response.sendRedirect(requestToken.getAuthenticationURL());

    }catch(TwitterException e) {
		    throw new ServletException(e);
		}
        
 } 
}
    
    
    

