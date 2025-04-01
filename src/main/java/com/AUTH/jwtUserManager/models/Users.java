package com.AUTH.jwtUserManager.models;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Column(name="profile_pic")
    private String profilePicUrl;


    public Users(){}
    public Users(String username, String password, String profilePicUrl){
        this.username=username;
        this.password=password;
        this.profilePicUrl=profilePicUrl;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public String getProfilePicUrl(){
        return profilePicUrl;
    }
    public void setProfilePicUrl(String profilePicUrl){
        this.profilePicUrl=profilePicUrl;
    }
}
