package com.users.api.controller;

import com.users.api.exceptions.RoleAlreadyAssignedException;
import com.users.api.exceptions.RoleNotFoundException;
import com.users.api.model.Roles;
import com.users.api.model.User;
import com.users.api.service.RolesService;
import com.users.api.service.UserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Validated
public class RolesController {
    @Autowired
    private RolesService rolesService;
    @Autowired
    private UserService userService;

    @GetMapping("user/{userID}")
    public ResponseEntity<Object> getRolesByUserID(@PathVariable("userID") int userID) {
        try {
            List<String> roles = rolesService.getRolesByUserID(userID);
            return ResponseEntity.ok().body(roles);
        } catch (RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{roleType}")
    public ResponseEntity<Object> getUsersByRoleType(@PathVariable @NotBlank String roleType) {
        List<Integer> users = rolesService.getUsersByRoleType(roleType);
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            String message = "No users found for role type: " + roleType;
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> assignRoleToUser(@RequestBody Roles request) {
        int userID = request.getUserID();
        String roleType = request.getRoleType();
        try {
            rolesService.assignRoleToUser(userID, roleType);
            // Check if the user exists before accessing their email
            User user = userService.getUserById(userID);
            if (user != null) {
                return ResponseEntity.ok().body("Role '" + roleType + "' assigned to user " + user.getEmail().split("@")[0].toUpperCase());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + userID + " not found.");
            }
        } catch (RoleAlreadyAssignedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already has the role '" + roleType + "' assigned.");
        }
    }

    @DeleteMapping("/user/{userID}/{roleType}")
    public ResponseEntity<Object> removeRoleFromUser(@PathVariable("userID") int userID, @PathVariable("roleType") String roleType) {
        try {
            rolesService.removeRoleFromUser(userID, roleType);
            return ResponseEntity.ok().body("Role '" + roleType + "' removed from user " + userService.getUserById(userID).getEmail().split("@")[0].toUpperCase());
        } catch (RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
