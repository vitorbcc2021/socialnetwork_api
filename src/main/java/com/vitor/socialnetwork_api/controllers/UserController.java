package com.vitor.socialnetwork_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.vitor.socialnetwork_api.repositories.UserRepository;

@RestController
public class UserController {
    
    @Autowired
    UserRepository userRepository;

    
}
