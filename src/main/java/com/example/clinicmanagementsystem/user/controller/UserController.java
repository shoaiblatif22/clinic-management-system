package com.example.clinicmanagementsystem.user.controller;
import com.example.clinicmanagementsystem.user.entity.UserEntity;
import com.example.clinicmanagementsystem.user.model.UserModel;
import com.example.clinicmanagementsystem.user.service.UserService;
import com.example.clinicmanagementsystem.role.entity.RoleEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET REQUEST TO FIND ALL USERS
     * @param = no params
     * @return  all users
     * */

    @GetMapping("/users")
    public List<UserModel> findAllUsers() {
        return userService.findAll();
    }

    /**
     * GET REQUEST TO FIND USER WITH UNIQUE ID
     * @param id            user_id
     * @return              user with status code of 200
     * */
    @GetMapping("users/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Integer id) {
        try {
            UserModel user = userService.getUserById(id)
                    .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
            return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    /**
    * POST REQUEST TO SAVE USER TO DB
    * @param
    * @return           saves User
    * */
    @PostMapping("/users")
    public UserModel save(@RequestBody UserEntity user){
        return userService.saveUser(user);
    }

    /**
     * PUT MAPPING TO UPDATE USER DETAILS
     * @param id        user_id
     * @return          updated user
     * */
    @PutMapping("/users/{id}")
    public UserModel update(@PathVariable Integer id, @RequestBody UserModel user) {
        return userService.update(id, user);
    }

    /**
    * DELETE REQUEST TO DELETE USER
    * @param id         user_id
    * @return           deleted user
    * */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable Integer id) {
        if (userService.getUserById(id).isEmpty()) {
            throw new RuntimeException("User id not found - " + id);
        } else {
            userService.deleteUserById(id);
        }
        return null;
    }

    /**
     * GET USER BY USER_ID AND ROLE
     * @param id            user_id
     * @param roleId        role id
     * @return              ResponseEntity with user details or error
     */
    @GetMapping("/users/{id}/roles/{roleId}")
    public ResponseEntity<UserModel> getUserRole(@PathVariable Integer id, @PathVariable Integer roleId) {
        UserModel userModel = userService.getUserById(id)
                .orElseThrow(null);
        if (userModel == null) {
            throw new RuntimeException("User id not found - " + id);
        }
        if(userModel.isValidated()) {
            return new ResponseEntity<>(userModel, HttpStatus.OK);
        }
        return null;
    }

    /*This needs fixing for later. Other mappings work*/
//    @GetMapping("/users/firstName/{firstName}")
//    public ResponseEntity<List<UserModel>> findUserFirstName(@RequestParam String firstName) {
//        List<UserModel> users = (List<UserModel>) userService.findByFirstName(firstName); // Assuming findByFirstName now handles null returns
//        if (users == null) {
//            users = Collections.emptyList(); // Or return ResponseEntity.noContent().build();
//        }
//        return new ResponseEntity<>(users, HttpStatus.OK); // Use HttpStatus.OK instead of valueOf(200)
//    }
}
