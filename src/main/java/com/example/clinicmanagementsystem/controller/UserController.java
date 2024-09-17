package com.example.clinicmanagementsystem.controller;
import com.example.clinicmanagementsystem.entity.UserEntity;
import com.example.clinicmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //GET REQUEST TO FIND ALL USERS
    @GetMapping("/users")
    public List<UserEntity> findAll() {
        return userService.findAll();
    }

    //GET REQUEST TO FIND USER WITH UNIQUE ID
    @GetMapping("users/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
        try {
            UserEntity user = userService.getUserById(id)
                    .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
            return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
    //GET REQUEST TO FIND USER BY FIRST NAME
    @GetMapping("/users/first-name")
    public ResponseEntity<List<UserEntity>> findByFirstName(@RequestParam String firstName) {
        List<UserEntity> users = Collections.singletonList(userService.findByFirstName(firstName));
        return new ResponseEntity<>(users, HttpStatusCode.valueOf(200));
    }

    //POST REQUEST TO SAVE USER TO DB
    @PostMapping("/users")
    public UserEntity save(@RequestBody UserEntity user){
        return userService.saveUser(user);
    }

    //DELETE REQUEST TO DELETE USER
    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable Integer id) {
        if (userService.getUserById(id).isEmpty()) {
            throw new RuntimeException("User id not found - " + id);
        } else {
            userService.deleteUserById(id);
        }
        return null;
    }
}
