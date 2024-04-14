package com.users.api.repository;

import com.users.api.exceptions.UserNotFoundExceptionToDeleteException;
import com.users.api.exceptions.UserNotFoundExceptionToUpdateException;
import com.users.api.exceptions.DuplicateUserException;
import com.users.api.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final List<User> dummyUserData = new ArrayList<>() {{
        add(new User(1, "tim@eu.com", "timpassword"));
        add(new User(2, "mike@eu.com", "mikepassword"));
        add(new User(3, "paul@eu.com", "paulpassword"));
        add(new User(4, "cristina@eu.com", "cristinapassword"));
        add(new User(5, "simida@eu.com", "simidapassword"));
        add(new User(6, "alin@eu.com", "alinpassword"));
        add(new User(7, "flavi@eu.com", "flavipassword"));
        add(new User(8, "andrei@eu.com", "andreipassword"));
        add(new User(9, "mihai@eu.com", "mihaipassword"));
        add(new User(10, "alina@eu.com", "alinapassword"));
        add(new User(11, "darius@eu.com", "dariuspassword"));
    }};

    public List<User> getAllUsers() {
        return dummyUserData;
    }

    public void updateUser(User updatedUser) throws UserNotFoundExceptionToUpdateException {
        boolean found = false;
        for (int i = 0; i < dummyUserData.size(); i++) {
            User user = dummyUserData.get(i);
            if (user.getId() == updatedUser.getId()) {
                dummyUserData.set(i, updatedUser);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new UserNotFoundExceptionToUpdateException(updatedUser.getId());
        }
    }

    public void deleteUserById(int id) throws UserNotFoundExceptionToDeleteException {
        Iterator<User> iterator = dummyUserData.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new UserNotFoundExceptionToDeleteException(id);
        }
    }

    public User findUserByID(int id) {
        Optional<User> foundUser = dummyUserData.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
        return foundUser.orElse(null);
    }

    public User addUser(User newUser) throws DuplicateUserException {
        // Check for duplicate emails
        boolean isDuplicate = dummyUserData.stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(newUser.getEmail()));

        if (isDuplicate) {
            throw new DuplicateUserException(newUser.getEmail());
        } else {
            // Assign a new ID to the new user
            int newId = dummyUserData.size() + 1;
            newUser.setId(newId);
            dummyUserData.add(newUser);
        }
        return newUser;
    }
}
