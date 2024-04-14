package com.users.api.service;

import com.users.api.exceptions.DuplicateUserException;
import com.users.api.exceptions.UserNotFoundExceptionToDeleteException;
import com.users.api.exceptions.UserNotFoundExceptionToUpdateException;
import com.users.api.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(int id);

    User addUser(User newUser) throws DuplicateUserException;

    User updateUser(int id, User updatedUser) throws UserNotFoundExceptionToUpdateException;

    void deleteUser(int id) throws UserNotFoundExceptionToDeleteException;
}
