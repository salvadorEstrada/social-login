/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.develop.ws;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.com.develop.controller.UserController;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.RequestToken;

/**
 * REST Web Service
 *
 * @author ESTRADA
 */
@Path("/twitter")
public class TWCallback {

   
    @EJB
    private UserController userController;
    
    public TWCallback() {
    }

    @Context private HttpServletRequest request;
    @Context private HttpServletResponse response; 
    
    @Path("/callback")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCall(@QueryParam("oauth_verifier")String verifier ) throws TwitterException, IOException { 
        Twitter twitter =(Twitter) request.getSession().getAttribute("twitter");  
        //System.out.println("Twitter: "+twitter);
        RequestToken requestToken =(RequestToken)request.getSession().getAttribute("requestToken");  
       System.out.println("Token: "+requestToken); 
        ServletOutputStream out = response.getOutputStream();
        try
        {
           // AccessToken accessToken =twitter.getOAuthAccessToken(requestToken,verifier);  
            request.getSession().removeAttribute("requestToken");  
            User user; 
            String profile = twitter.getAccountSettings().getScreenName(); 
            user = twitter.showUser(profile); 
            Long ide = user.getId();  
            String id =String.valueOf(ide);
            String name = user.getName(); 
            String email =user.getEmail(); 
            String picture=user.get400x400ProfileImageURL(); 
            
            if(userController.verifyUserProfile(id,name,email,picture).isEmpty())
            {
                userController.addUserProfile(id, name, email, picture);  
                return Response.status(Response.Status.OK).entity(" Service: The User Profile has been sent to database!").build();
            }
            else
            {
               return Response.status(Response.Status.CONFLICT).entity(" Service: This object already exists!"+email).build();   
            }
            
       
        }catch(TwitterException  e)
        {
            System.out.println(e.getMessage());
        }
     return Response.status(400).entity("The information could not proccess by server").build(); 
    }
}
