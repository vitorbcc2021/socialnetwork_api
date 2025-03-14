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
@Table(name = "post")
public class PostModel extends RepresentationModel<PostModel> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID postID;

    private UserModel userModel;
    private String imgPath; 
    private int likes;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public UUID getPostID() {
        return postID;
    }

    public void setPostID(UUID postID) {
        this.postID = postID;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getLikes() {
        return likes;
    }
    
    public void setLikes(int likes) {
        this.likes = likes;
    }
}
