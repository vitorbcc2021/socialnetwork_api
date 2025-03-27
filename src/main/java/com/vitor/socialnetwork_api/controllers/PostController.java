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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitor.socialnetwork_api.dtos.PostDto;
import com.vitor.socialnetwork_api.models.PostModel;
import com.vitor.socialnetwork_api.models.UserModel;
import com.vitor.socialnetwork_api.repositories.PostRepository;
import com.vitor.socialnetwork_api.repositories.UserRepository;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/post")
public class PostController {
    

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;


    @PostMapping("/")
    public ResponseEntity<?> addPost(@RequestBody @Valid PostDto dto) {
        try {
            UUID authorId = UUID.fromString(dto.authorId());
            
            UserModel author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            PostModel post = new PostModel();
            post.setAuthor(author);
            post.setImgPath(dto.imgPath());
            post.setLikes(dto.likes());
            
            return ResponseEntity.ok(postRepository.save(post));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID do usuário inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getPost (@PathVariable("id") UUID id){
        Optional<PostModel> postOptional = postRepository.findById(id);

        if(postOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        postOptional.get().add(linkTo(methodOn(PostController.class).getAllPosts()).withRel("Post List"));

        return ResponseEntity.status(HttpStatus.OK).body(postOptional.get());
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPostsByUser(@PathVariable String userId) {
        try {
            UUID userUuid = UUID.fromString(userId); // Converte String para UUID
            List<PostModel> posts = postRepository.findByAuthorId(userUuid);
            
            if (posts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum post encontrado para este usuário");
            }
            
            return ResponseEntity.ok(posts);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID de usuário inválido");
        }
    }


    @GetMapping("/")
    public ResponseEntity<Object> getAllPosts (){
        List<PostModel> posts = postRepository.findAll();

        if(!posts.isEmpty())
            for(PostModel post : posts)
                post.add(linkTo(methodOn(PostController.class).getPost(post.getPostID())).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable("id") UUID id, @RequestBody @Valid PostDto dto) {
        Optional<PostModel> postOptional = postRepository.findById(id);

        if(postOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found!");
        
        PostModel post = postOptional.get();
        BeanUtils.copyProperties(dto, post);

        return ResponseEntity.status(HttpStatus.OK).body(postRepository.save(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable("id") UUID id){
        Optional<PostModel> postOptional = postRepository.findById(id);

        if(postOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found!");

        postRepository.delete(postOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Post deleted!");
    }
}
