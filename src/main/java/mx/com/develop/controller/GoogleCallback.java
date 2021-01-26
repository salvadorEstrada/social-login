package mx.com.develop.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.com.develop.entity.SocialModel;
import org.json.JSONObject;

//Author  SALVADOR ESTRADA 

@WebServlet(name = "GoogleCallBack", urlPatterns = {"/googleCallback"})
public class GoogleCallback extends HttpServlet {
    
   @EJB 
   private  GoogleConnection googleConnection;
   @EJB 
   private  UserController userController;
   final private static Logger LOGGER =Logger.getLogger("bitacora.subnivel.control"); 
   
    HttpSession session;  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        
              //PrintWriter ou = response.getWriter(); 
             // out.println ("Este es un servlet de prueba"); 
       
                        String cadenaJson=""; 
                        
			if (request.getParameter("code") != null && request.getParameter("state") != null)
                        {
                                
                               cadenaJson =googleConnection.getUserInfoJson(request.getParameter("code")); 
                               
                        } 
                        
                        JSONObject json = new JSONObject(cadenaJson); 
                        String id = json.getString("id");  
                        String name = json.getString("name");
                        String email = json.getString("email");
                        String picture = json.getString("picture"); 
                        ServletOutputStream out = response.getOutputStream();
                        System.out.println(id);
                        System.out.println(name);
                        System.out.println(email);
                        System.out.println(picture);   
                        out.println("<h1>Google Login using Java</h1>");
		        out.println("<h2>The user profile was sent to database</h2>");
                        out.println("<h2>Welcome: "+name+" this is your profile</h2>");
                        out.println("<div>Welcome "+id); 
                        out.println("<div>Welcome "+name);
                        out.println("<div>Your Email: "+email);
                        out.println("<div>Your Email: "+picture);
                        
                  try {
                      /*if (id != null && name != null && !id.trim().isEmpty() && !name.trim().isEmpty()) 
                      {
                        SocialModel userGoogle = new SocialModel(id,name,email,picture);  
                        if(!userController.validateUserProfileById(id).isEmpty())
                        {
                          throw new RuntimeException("This object already exists!");   
                        }else 
                        {
                        //userController.addUserProfile(userGoogle);
                        LOGGER.log(Level.INFO,"User google insertion sucessfull!");  
                        }
                        
                     }*/
                } catch(Exception e)
                {
                LOGGER.log(Level.WARNING, e.getMessage());
                }
                               
    } 
   
  }

