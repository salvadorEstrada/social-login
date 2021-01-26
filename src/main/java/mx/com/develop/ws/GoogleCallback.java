/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.develop.ws;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.com.develop.controller.GoogleConnection;
import mx.com.develop.controller.UserController;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author ESTRADA
 */
@Path("googlecallback")
public class GoogleCallback {

   
   @EJB 
   private  GoogleConnection googleConnection;
   @EJB 
   private  UserController userController; 
   URI uri; 

    public GoogleCallback() throws URISyntaxException {
        this.uri = new URI("http://localhost:8080/springboot-cliente-0.0.1-SNAPSHOT/views/cliente/");
    } 
    
   
    //AQUI SE VAN A MOSTRAR LOS PERFILES DE USUARIO, UNA VEZ ALMACENADOS EN LA BD
    @GET
    @Path("/profileList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response listProfile() {  
        
        if(userController.getUserProfile().isEmpty())
        {
           return Response.status(Response.Status.OK).entity("Service:User profile does not exist").build();  
            
        } 
        else
        {
           return Response.ok(userController.getUserProfile()).build();
               
        }
      
        
    } 
    
   //ESTE SERVICIO ES EL CALLBACK 
   //EN ESTE MËTODO DEL SERVICIO SE RECIBE EL (CODE Y STATE)   DE VERIFICACION DE LA API DE GOOGLE
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getGoogleUserProfile(@QueryParam("code")String code, @QueryParam("state")String state ) throws IOException {
        
        if(code==null|| state==null)
        {
            throw new RuntimeException("ERROR: Didn't  get code parameter in callback");
           
        }else if(code!=null && state !=null)
        {
          String cadenaJson =googleConnection.getUserInfoJson(code); 
          JSONObject objetoJson = new JSONObject(cadenaJson);  
          String id = objetoJson.getString("id");
          String name = objetoJson.getString("name");
          String email = objetoJson.getString("email");
          String picture = objetoJson.getString("picture");  
          
        
          //SocialModel userGoogle = new SocialModel(id,name,email,picture); 
          //Aquí se puede hacer la validación en la DB por cualquier campo
          if(!userController.verifyUserProfile(id,name,email,picture).isEmpty())
          {
             //return Response.status(Response.Status.OK).entity("Service: this id already exists!"+email).build();
             return  Response.temporaryRedirect(uri).build();
          } 
          else
          {
          //userController.addUserProfile(userGoogle);  
           userController.addUserProfile(id, name, email, picture);
          return Response.status(Response.Status.OK).entity("The user profile was sent to database!").build();
          }
         // return Response.status(Response.Status.OK).entity("Data was sent to data base"+code +" state: " +cadenaJson).build();
        }
       
       return Response.status(Response.Status.OK).entity("Data was sent to data base"+code +" state: " +state).build();
    }

   
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
