/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.develop.entity;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table; 

@Entity 
@Table(name="user_social")
public class SocialModel implements Serializable { 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ide")
    private Long Ide; 
    @Column(name = "id_profile")
    private String Id;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "name")
    private String name; 
    
     @Lob 
     @Column(name = "picture") 
     private String picture;  
     
      public SocialModel() {
         
      } 
     

    public SocialModel(String Id, String email,String name,String picture) {
        this.Id = Id;
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

    public Long getIde() {
        return Ide;
    }

    public void setIde(Long Ide) {
        this.Ide = Ide;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
     
}

   

