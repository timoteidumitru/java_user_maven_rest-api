package com.users.api.repository;

import com.users.api.exceptions.RoleAlreadyAssignedException;
import com.users.api.exceptions.RoleNotFoundException;
import com.users.api.model.Roles;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RolesRepository {
    private final List<Roles> rolesData = new ArrayList<>() {{
        add(new Roles(1, "member"));     // Tim
        add(new Roles(3, "member"));     // Paul
        add(new Roles(4, "member"));     // Cristina
        add(new Roles(5, "member"));     // Simida
        add(new Roles(6, "member"));     // Alin
        add(new Roles(7, "member"));     // Flavi
        add(new Roles(8, "admin"));      // Andrei
        add(new Roles(9, "admin"));      // Mihai
        add(new Roles(10, "trainer"));   // Alina
        add(new Roles(11, "trainer"));   // Darius
        add(new Roles(8, "trainer"));      // Andrei
        add(new Roles(9, "trainer"));      // Mihai
        add(new Roles(2, "genitor"));    // Mike
    }};

    public List<Roles> getAllRoles() {
        return rolesData;
    }

    public List<Integer> getUsersByRoleType(String roleType) {
        return rolesData.stream()
                .filter(roles -> roles.getRoleType().equalsIgnoreCase(roleType))
                .map(Roles::getUserID)
                .collect(Collectors.toList());
    }

    public List<String> getRolesByUserID(int userID) {
        List<String> userRoles = new ArrayList<>();
        for (Roles role : rolesData) {
            if (role.getUserID() == userID) {
                userRoles.add(role.getRoleType());
            }
        }
        return userRoles;
    }

    public void assignRoleToUser(int userID, String roleType) {
        boolean userFound = false;
        for (Roles roles : rolesData) {
            if (roles.getUserID() == userID) {
                // User found
                // Check if the role type already exists for the user
                boolean roleExists = false;
                for (Roles userRole : rolesData) {
                    if (userRole.getUserID() == userID && userRole.getRoleType().equalsIgnoreCase(roleType)) {
                        roleExists = true;
                        break;
                    }
                }
                if (!roleExists) {
                    // Role type does not exist, add it
                    rolesData.add(new Roles(userID, roleType));
                }
                userFound = true;
                break;
            }
        }
        if (!userFound) {
            // If the user ID is not found, add a new entry with the role type
            rolesData.add(new Roles(userID, roleType));
        }
    }

    public boolean removeRoleFromUser(int userID, String roleType) {
        for (Iterator<Roles> iterator = rolesData.iterator(); iterator.hasNext();) {
            Roles roles = iterator.next();
            if (roles.getUserID() == userID && roles.getRoleType().equals(roleType)) {
                iterator.remove();
                return true; // Role successfully removed
            }
        }
        return false; // Role not found
    }

}
