package com.example.clinicmanagementsystem.controller.user;

import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController()
@RequestMapping(value = "/registration")
@CrossOrigin(origins = "http://localhost:5173")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


//    @PostMapping("/register")
//    public ResponseEntity<RedirectView> register(@RequestBody UserModel user) {
//        try {
//            userService.registerUser(user);
//            RedirectView redirectView = new RedirectView("/api/auth/user/patient");
//            return ResponseEntity.status(HttpStatus.CREATED).body(redirectView);
//        } catch (Exception e) {
//            RedirectView redirectView = new RedirectView("/registration/error");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(redirectView);
//        }
//    }
}
