package com.users.api.controller;

import com.users.api.exceptions.DuplicateUserException;
import com.users.api.exceptions.UserNotFoundExceptionToDeleteException;
import com.users.api.exceptions.UserNotFoundExceptionToUpdateException;
import com.users.api.model.User;
import com.users.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") @Min(1) int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            String errorMessage = "User with ID " + id + " not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> addUser(@RequestBody @Valid User newUser) {
        try {
            User addedUser = userService.addUser(newUser);
            String message = "User with ID " + addedUser.getId() + " has been successfully added.";

            Map<String, Object> response = new HashMap<>();
            response.put("user", addedUser);
            response.put("message", message);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DuplicateUserException e) {
            String errorMessage = "User with email " + newUser.getEmail() + " already exists.";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") @Min(1) int id, @RequestBody @Valid User updatedUser) {
        try {
            User updated = userService.updateUser(id, updatedUser);
            String message = "User " + updated.getEmail().split("@")[0].toUpperCase() + " has been successfully updated.";

            Map<String, Object> response = new HashMap<>();
            response.put("user", updated);
            response.put("message", message);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundExceptionToUpdateException e) {
            String errorMessage = "User with ID " + id + " not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") @Min(1) int id) {
        try {
            String message = "User " + userService.getUserById(id).getEmail().split("@")[0].toUpperCase() + " has been successfully deleted.";
            userService.deleteUser(id);
            return ResponseEntity.ok().body(message);
        } catch (UserNotFoundExceptionToDeleteException e) {
            String errorMessage = "User with ID " + id + " not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}

