package com.example.restfulwebservices.user;

import com.example.restfulwebservices.user.User;
import com.example.restfulwebservices.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJpaResource {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/jpa/users")
    public List<User> retreiveAllUsers(){

        return userRepository.findAll();


}
//    @GetMapping("/jpa/users/{id}")
    public User retrieveUser(@PathVariable int id){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new UserNotFoundException("Unable to find user with id = "+id));
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUserHateoas(@PathVariable int id){
        EntityModel<User>  model = EntityModel.of(retrieveUser(id));
        //Without below code links will not get added.
        WebMvcLinkBuilder linkToUsers = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retreiveAllUsers());
        model.add(linkToUsers.withRel("all-users")); //This is to set the link to model
        return model;
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }


    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User saveUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}").buildAndExpand(saveUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveUserPosts(@PathVariable int id){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new UserNotFoundException("Unable to find user with id = "+id)).getPosts();
    }



    @PostMapping("/jpa/users/{id}/post")
    public ResponseEntity<Object> createUser(@PathVariable int id, @RequestBody Post post){
        User user = retrieveUser(id);
        post.setUser(user);
        postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


}

