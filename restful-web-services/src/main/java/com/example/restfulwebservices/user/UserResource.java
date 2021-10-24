package com.example.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    @Autowired
    private UserDaoService service;

    @GetMapping("/users")
    public List<User> retreiveAllUsers(){

        return service.findAll();

    }

//    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        return service.findOne(id);
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUserHateoas(@PathVariable int id){
        EntityModel<User>  model = EntityModel.of(retrieveUser(id));
        //Without below code links will not get added.
        WebMvcLinkBuilder linkToUsers = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retreiveAllUsers());
        model.add(linkToUsers.withRel("all-users")); //This is to set the link to model
        return model;
    }

    @DeleteMapping("/users/{id}")
    public User deleteUser(@PathVariable int id){
        return service.deleteById(id);
    }


    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User saveUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}").buildAndExpand(saveUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}

