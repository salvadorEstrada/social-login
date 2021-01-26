/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.develop.entity;

import javax.persistence.Column;
import javax.persistence.Lob;

/**
 *
 * @author ESTRADA
 */
public class SocialDto { 
    @Column(name = "id_profile")
    private String Id;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "name")
    private String name; 
    
    @Lob 
    @Column(name = "picture") 
    private String picture;  

    public SocialDto(String Id, String email, String name, String picture) {
        this.Id = Id;
        this.email = email;
        this.name = name;
        this.picture = picture;
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
