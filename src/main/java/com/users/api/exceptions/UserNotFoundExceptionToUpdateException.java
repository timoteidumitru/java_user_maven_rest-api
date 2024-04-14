package com.users.api.exceptions;

public class UserNotFoundExceptionToUpdateException extends Exception {
    public UserNotFoundExceptionToUpdateException(int userId) {
        super("User with ID " + userId + " not found to update.");
    }
}
