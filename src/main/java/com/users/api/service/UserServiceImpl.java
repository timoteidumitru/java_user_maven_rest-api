package com.users.api.service;

import com.users.api.exceptions.DuplicateUserException;
import com.users.api.exceptions.UserNotFoundExceptionToDeleteException;
import com.users.api.exceptions.UserNotFoundExceptionToUpdateException;
import com.users.api.model.User;
import com.users.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findUserByID(id);
    }

    @Override
    public User addUser(User newUser) throws DuplicateUserException {
        return userRepository.addUser(newUser);
    }

    @Override
    public User updateUser(int id, User updatedUser) throws UserNotFoundExceptionToUpdateException {
        updatedUser.setId(id); // Ensure ID from path variable is set
        userRepository.updateUser(updatedUser);
        return updatedUser;
    }

    @Override
    public void deleteUser(int id) throws UserNotFoundExceptionToDeleteException {
        userRepository.deleteUserById(id);
    }
}
