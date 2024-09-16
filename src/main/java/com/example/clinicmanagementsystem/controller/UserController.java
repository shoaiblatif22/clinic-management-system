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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserEntity> findAll() {
        return userService.findAll();
    }

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
    @GetMapping("/users/first-name")
    public ResponseEntity<List<UserEntity>> findByFirstName(@RequestParam String firstName) {
        List<UserEntity> users = Collections.singletonList(userService.findByFirstName(firstName));
        return new ResponseEntity<>(users, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/users")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity user) {
        UserEntity savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatusCode.valueOf(201));
    }


}
