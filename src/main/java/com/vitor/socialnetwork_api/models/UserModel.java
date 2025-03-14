package com.vitor.socialnetwork_api.models;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class UserModel extends RepresentationModel<UserModel> implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userID;

    private String name;
    private String email;
    private String profilePicture;
    private String banner;
    private int followers;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

}
