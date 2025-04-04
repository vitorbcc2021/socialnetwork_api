package com.vitor.socialnetwork_api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitor.socialnetwork_api.dtos.LoginDto;
import com.vitor.socialnetwork_api.dtos.UserCreationDto;
import com.vitor.socialnetwork_api.dtos.UserUpdateDto;
import com.vitor.socialnetwork_api.models.UserModel;
import com.vitor.socialnetwork_api.repositories.UserRepository;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {
    

    @Autowired
    UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/")
    public ResponseEntity<UserModel> addUser(@RequestBody @Valid UserCreationDto dto){
        UserModel user = new UserModel();

        BeanUtils.copyProperties(dto, user);

        user.setPassword(passwordEncoder.encode(dto.password()));

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }


    @PostMapping("/login")
    public ResponseEntity<Object> getByLogin(@RequestBody @Valid LoginDto loginDto) {
        Optional<UserModel> userOptional = userRepository.findByEmail(loginDto.email());

        if (userOptional.isEmpty() || !passwordEncoder.matches(loginDto.password(), userOptional.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        UserModel user = userOptional.get();
        user.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());

        return ResponseEntity.ok(user);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser (@PathVariable("id") UUID id){
        Optional<UserModel> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        userOptional.get().add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("User List"));

        return ResponseEntity.ok(userOptional.get());
    }


    @GetMapping("/")
    public ResponseEntity<Object> getAllUsers (){
        List<UserModel> users = userRepository.findAll();

        if(!users.isEmpty())
            for(UserModel user : users)
                user.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());

        return ResponseEntity.ok(users);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") UUID id, @RequestBody @Valid UserUpdateDto dto) {
        
        Optional<UserModel> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
        
        UserModel user = userOptional.get();
        
        BeanUtils.copyProperties(dto, user);

        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") UUID id){
        Optional<UserModel> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");

        String name = userOptional.get().getName();
        userRepository.delete(userOptional.get());

        return ResponseEntity.ok(name + " is now deleted!");
    }
}
