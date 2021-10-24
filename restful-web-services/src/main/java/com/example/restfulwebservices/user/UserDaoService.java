package com.example.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDaoService {
    private static int usersCount = 1;
    private static List<User> users = new ArrayList<>();

    public UserDaoService() {
    }

    static{
        users.add(new User(usersCount++, "Adam", new Date()));
        users.add(new User(usersCount++, "Eve", new Date()));
        users.add(new User(usersCount++, "Jack", new Date()));

    }

    public List<User> findAll(){
        return users;
    }



    public User save(User user){
        user.setId(usersCount++);
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        Optional<User> optionalUser = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }else{
            throw new UserNotFoundException("id "+ id);
        }

    }

    public User deleteById(int id){
        User foundUser = findOne(id);
        users = users.stream().filter(user -> user.getId()!=id).collect(Collectors.toList());
        return foundUser;
    }
}
