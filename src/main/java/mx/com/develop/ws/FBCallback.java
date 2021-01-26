/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.develop.ws;

import java.util.Map;
import javax.ejb.EJB;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.com.develop.controller.FBConnection;
import mx.com.develop.controller.FBGraph;
import mx.com.develop.controller.UserController;

/**
 * REST Web Service
 *
 * @author ESTRADA
 */
@Path("fbcallback")
public class FBCallback {

    //@Context
    //private UriInfo context; 
    
    @EJB
    private FBConnection fbConnection;
    @EJB
    private UserController userController;
   

    public FBCallback() {
    }
    //ESTE SERVICIO ES EL CALLBACK 
   //EN ESTE MËTODO DEL SERVICIO SE RECIBE EL CODIGO(TOKEN) DE VERIFICACION DE LA API DE FACEBOOK
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFBUserProfile(@QueryParam("code")String code ) {
        
        if(code.equals("")|| code==null)
        {
            throw new RuntimeException("ERROR: Didn't  get code parameter in callback");
           
        }
        else
        {
           String accessToken = fbConnection.getAccess(code); 
           FBGraph fbGraph = new FBGraph(accessToken); //revisar esta linea
           String graph = fbGraph.getFBGraph(); 
           Map<String, String> fbProfileData = fbGraph.getGraphData(graph);  
           
                String id = fbProfileData.get("id");  
                String name = fbProfileData.get("name");
                String email = fbProfileData.get("email"); 
                String picture =fbProfileData.get("url");
                
         
         
         if(userController.verifyUserProfile(id,name,email,picture).isEmpty())
         {
             userController.addUserProfile(id, name, email, picture); 
             
             return Response.status(Response.Status.NOT_FOUND).entity(" Service: The User Profile has been sent to database!").build();
         }
         else
         {
             return Response.status(Response.Status.NOT_FOUND).entity(" Service: This object already exists!"+email).build();
         }
         
        }
        
    }

 
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
