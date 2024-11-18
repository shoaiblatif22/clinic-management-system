package com.example.clinicmanagementsystem.user.controller;
import com.example.clinicmanagementsystem.role.Role;
import com.example.clinicmanagementsystem.role.service.RoleService;
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
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * GET REQUEST TO FIND ALL USERS
     *
     * This endpoint retrieves a list of all users from the database. It does not require any parameters.
     * The method returns a list of `UserModel` objects representing all users in the system.
     *
     * @return  A `List<UserModel>` containing all users. The list will be returned with a status code of 200 (OK).
     *
     * Note: If there are no users in the system, this method will return an empty list, which will be
     * represented as an empty array or list in the response.
     */
    @GetMapping("/users")
    public List<UserModel> findAllUsers() {
        return userService.findAll();
    }

    /**
     * GET REQUEST TO FIND USER WITH UNIQUE ID
     *
     * This endpoint retrieves a user by their unique ID. The user ID is passed in the URL as a path variable.
     * If the user with the given ID exists, the user details are returned with a status code of 200 (OK).
     * If the user is not found, a `NoSuchElementException` is thrown, and an appropriate error response is returned.
     *
     * @param id            The unique ID of the user to be retrieved.
     *                      This ID is passed as a path variable in the URL.
     * @return              A `ResponseEntity` containing the `UserModel` of the found user and an HTTP status of 200 (OK).
     *                      If the user is not found, an exception is thrown and handled with an appropriate error response.
     *
     * Note: The method throws a `NoSuchElementException` if no user is found with the provided ID, which is
     * subsequently handled by the exception handler in the controller or global exception handler.
     */
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
     * POST REQUEST TO SAVE USER TO DATABASE
     *
     * This endpoint allows saving a new user to the database. The user details are provided
     * in the request body as a JSON object, which will be converted to a `UserEntity` object.
     * The saved user is then returned as a `UserModel`.
     *
     * @param user      The user details to be saved, provided as a JSON object in the request body.
     *                  This is converted into a `UserEntity` object before saving to the database.
     * @return          The saved user, returned as a `UserModel` object containing the saved user’s data.
     *
     * Note: The method expects the client to send valid user data in the request body. If the data is
     * invalid or incomplete, the method may throw a validation error or return a `400 BAD REQUEST`.
     */
    @PostMapping("/users")
    public UserModel save(@RequestBody UserEntity user){
        UserModel savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED).getBody();
    }

    /**
     * PUT REQUEST TO UPDATE USER DETAILS
     *
     * This endpoint allows updating the details of an existing user. The user is identified
     * by their unique ID, and the updated information is passed in the request body.
     * If the user with the specified ID exists, their details will be updated; otherwise,
     * a 404 NOT FOUND error will be thrown.
     *
     * @param id        The unique identifier of the user to be updated (User ID).
     * @param user      The updated user details, provided as a JSON object in the request body.
     * @return          The updated user model containing the latest details after the update.
     *
     * Note: This method should ideally return an updated UserModel in a ResponseEntity
     * with status 200 (OK).
     */
    @PutMapping("/users/{id}")
    public UserModel update(@PathVariable Integer id, @RequestBody UserModel user) {
        return userService.update(id, user);
    }

    /**
     * DELETE REQUEST TO DELETE A USER
     *
     * This endpoint deletes a user identified by their unique user ID. If the user with
     * the specified ID does not exist, an exception is thrown. If the user exists,
     * the user is deleted from the database.
     *
     * @param id         The unique identifier of the user (User ID) to be deleted.
     * @return           A ResponseEntity containing the result of the deletion:
     *                   - HTTP 204 NO CONTENT if the user is successfully deleted.
     *                   - HTTP 404 NOT FOUND if the user with the specified ID does not exist.
     *
     * Note: This method currently returns null but should ideally return a proper ResponseEntity
     * with status code 204 (NO CONTENT) upon successful deletion.
     */
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
     * GET REQUEST TO CHECK IF A USER HAS A SPECIFIC ROLE
     *
     * This endpoint checks whether a user, identified by their unique user ID, has a role
     * with the specified role ID. It retrieves the user and role from the database and compares
     * the user's role with the requested role to determine if they match.
     *
     * @param id            The unique identifier of the user (User ID).
     * @param roleId        The unique identifier of the role (Role ID).
     * @return              A ResponseEntity containing the result of the check:
     *                      - HTTP 200 OK if the user has the specified role.
     *                      - HTTP 403 FORBIDDEN if the user does not have the specified role.
     *                      - HTTP 404 NOT FOUND if the role with the specified role ID is not found.
     */
    @GetMapping("/users/{id}/roles/{roleId}")
    public ResponseEntity<String> getUserRole(@PathVariable Integer id, @PathVariable Integer roleId) {

        //FETCH USER
        UserModel userModel = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User id not found - " + id));

        //FETCH THE ROLE ENTITY BY ROLE SERVICE
        Optional<RoleEntity> roleEntityOptional = roleService.getRoleById(roleId);

        if (roleEntityOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Role with ID " + roleId + " not found.");
        }

        //GET ROLE NAME FROM ROLE ENTITY
        String roleName = String.valueOf(roleEntityOptional.get().getRoleName());

        //CHECK IF USERS NAME MATCHES ROLE ENTITY
        if (userModel.getRole().equals(roleName)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User has the specified role with ID " + roleId + " (" + roleName + ")");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User does not have the specified role with ID " + roleId + " (" + roleName + ")");
        }
    }
}






    /*This needs fixing for later. Other mappings work*/
//    @GetMapping("/users/firstName/{firstName}")
//    public ResponseEntity<List<UserModel>> findUserFirstName(@RequestParam String firstName) {
//        List<UserModel> users = (List<UserModel>) userService.findByFirstName(firstName); // Assuming findByFirstName now handles null returns
//        if (users == null) {
//            users = Collections.emptyList(); // Or return ResponseEntity.noContent().build();
//        }
//        return new ResponseEntity<>(users, HttpStatus.OK); // Use HttpStatus.OK instead of valueOf(200)
//

