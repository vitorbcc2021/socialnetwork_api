package com.vitor.socialnetwork_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.vitor.socialnetwork_api.repositories.PostRepository;

@RestController
public class PostController {
    
    @Autowired
    PostRepository postRepository;
    
}
