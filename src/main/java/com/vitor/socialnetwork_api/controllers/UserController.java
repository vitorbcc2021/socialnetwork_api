package com.vitor.socialnetwork_api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vitor.socialnetwork_api.dtos.UserDto;
import com.vitor.socialnetwork_api.models.UserModel;
import com.vitor.socialnetwork_api.repositories.UserRepository;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    

    @Autowired
    UserRepository userRepository;


    @PostMapping("/user/")
    public ResponseEntity<UserModel> addUser(@RequestBody @Valid UserDto dto){
        UserModel user = new UserModel();

        BeanUtils.copyProperties(dto, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUser (@PathVariable("id") UUID id){
        Optional<UserModel> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        userOptional.get().add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("User List"));

        return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
    }


    @GetMapping("/user/")
    public ResponseEntity<Object> getAllUsers (){
        List<UserModel> users = userRepository.findAll();

        if(!users.isEmpty())
            for(UserModel user : users)
                user.add(linkTo(methodOn(UserController.class).getUser(user.getUserID())).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") UUID id, @RequestBody @Valid UserDto dto) {
        Optional<UserModel> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        
        UserModel user = userOptional.get();
        BeanUtils.copyProperties(dto, user);

        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(user));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") UUID id){
        Optional<UserModel> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");

        String name = userOptional.get().getName();
        userRepository.delete(userOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body(name + " is now deleted!");
    }
}
