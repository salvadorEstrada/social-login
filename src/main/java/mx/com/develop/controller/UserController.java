/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.develop.controller;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import mx.com.develop.entity.SocialDto;
import mx.com.develop.entity.SocialModel;

/**
 *
 * @author ESTRADA
 */
@Stateless
public class UserController { 
    
   @PersistenceContext  
   
   private EntityManager em; 
   
   //METHODO JUST FOR SHOW USER PROFILE
    public List<SocialModel> getUserProfile() 
   { 
      String query = "SELECT NEW mx.com.develop.entity.SocialDto(s.Id, s.email, s.name, s.picture) FROM SocialModel s"; 
      TypedQuery<SocialDto> typedQuery = em.createQuery(query, SocialDto.class); 
      List resultProfile = typedQuery.getResultList(); 
      return resultProfile;
   } 
    
     
  
   //METHOD I OF PERSISTING IN DB WITH JPA
  /* public void addUserProfile(SocialModel  socialUser) 
   {   
      em.persist(socialUser);  
   } */
   //METHOD II OF PESISTING IN DB WITH JPA 
   
   public void addUserProfile(String id,  String name, String email, String picture)
   { 
       SocialModel userProfile = new SocialModel();
            userProfile.setId(id);
            userProfile.setName(name);
            userProfile.setEmail(email);
            userProfile.setPicture(picture);
       em.persist(userProfile);
   } 
   
   
   //METHOD TO VERIFY IF THE USER PROFILE ALREADY EXISTS 
   //ESTE METODO COMPARA POR TODOS LOS CAMPOS PARA SABER SI YA EXISTE EL REGISTRO
   public List<SocialModel> verifyUserProfile(String id,String name, String email,String picture)
   {
      return em.createQuery("SELECT s FROM SocialModel s WHERE  s.Id=:id AND s.name=:name AND s.email=:email AND s.picture=:picture ")
              .setParameter("id", id)
              .setParameter("name", name)
              .setParameter("email", email)
              .setParameter("picture", picture).getResultList();
   }
   
   
   
   /* public List<SocialModel> validateUserProfileById(String userId) 
    { 
     
      String strQuery ="SELECT u from SocialModel u where u.Id=:id";
      Query  query= em.createQuery(strQuery,SocialModel.class);
      List resultList = query.setParameter("id", userId).getResultList(); 
      return resultList;
    }*/ 
    
}