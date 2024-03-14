package com.datasack.rps.controller;

import com.datasack.rps.entity.User;
import com.datasack.rps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/find-all-users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> userList = userService.getAllUsers();
        return userList!=null ? new ResponseEntity<>(userList, HttpStatus.OK)
                : new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/find-user-by-id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return user != null? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(new User(), HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/create-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user){
        if(user == null) {
            throw new RuntimeException("Exception while creating the user due to invalid request data");
        }

        user = userService.create(user);
        return (user!=null)?new ResponseEntity<>(user, HttpStatus.OK)
                :new ResponseEntity<>(new User(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-user-by-id/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        if(id == null) {
            return new ResponseEntity<String>("Invalid id to delete user", HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(id);
        return new ResponseEntity<String>("User deleted successfully with give id of " + id, HttpStatus.OK);
    }
}
