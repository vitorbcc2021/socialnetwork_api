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

import com.vitor.socialnetwork_api.dtos.PostDto;
import com.vitor.socialnetwork_api.models.PostModel;
import com.vitor.socialnetwork_api.repositories.PostRepository;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PostController {
    

    @Autowired
    PostRepository postRepository;


    @PostMapping("/post/")
    public ResponseEntity<PostModel> addPost(@RequestBody @Valid PostDto dto){
        PostModel post = new PostModel();

        BeanUtils.copyProperties(dto, post);

        return ResponseEntity.status(HttpStatus.CREATED).body(postRepository.save(post));
    }


    @GetMapping("/post/{id}")
    public ResponseEntity<Object> getPost (@PathVariable("id") UUID id){
        Optional<PostModel> postOptional = postRepository.findById(id);

        if(postOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        postOptional.get().add(linkTo(methodOn(PostController.class).getAllPosts()).withRel("Post List"));

        return ResponseEntity.status(HttpStatus.OK).body(postOptional.get());
    }


    @GetMapping("/post/")
    public ResponseEntity<Object> getAllPosts (){
        List<PostModel> posts = postRepository.findAll();

        if(!posts.isEmpty())
            for(PostModel post : posts)
                post.add(linkTo(methodOn(PostController.class).getPost(post.getPostID())).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }


    @PutMapping("/post/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable("id") UUID id, @RequestBody @Valid PostDto dto) {
        Optional<PostModel> postOptional = postRepository.findById(id);

        if(postOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found!");
        
        PostModel post = postOptional.get();
        BeanUtils.copyProperties(dto, post);

        return ResponseEntity.status(HttpStatus.OK).body(postRepository.save(post));
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable("id") UUID id){
        Optional<PostModel> postOptional = postRepository.findById(id);

        if(postOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found!");

        postRepository.delete(postOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Post deleted!");
    }
}
