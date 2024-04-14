package com.users.api.exceptions;

public class UserNotFoundExceptionToDeleteException extends Exception {
    public UserNotFoundExceptionToDeleteException(int userId) {
        super("User with ID " + userId + " not found to delete.");
    }
}

