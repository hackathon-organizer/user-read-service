package com.hackathonorganizer.userreadservice.user.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User with id %d not found in base!".formatted(id));
    }

    public UserNotFoundException(String keycloakId) {
        super("User with keycloak id %s not found in base!".formatted(keycloakId));
    }
}
