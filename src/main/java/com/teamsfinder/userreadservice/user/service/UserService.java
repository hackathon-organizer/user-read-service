package com.teamsfinder.userreadservice.user.service;

import com.teamsfinder.userreadservice.user.dto.UserResponseDto;
import com.teamsfinder.userreadservice.user.exception.UserNotFoundException;
import com.teamsfinder.userreadservice.user.model.User;
import com.teamsfinder.userreadservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDto> getAllUsers() {
        List<User> users = getAllFromRepository();
        return mapUsersToDto(users);
    }

    private List<UserResponseDto> mapUsersToDto(List<User> users) {
        return users.stream()
                .map(this::mapUserToDto)
                .toList();
    }

    private List<User> getAllFromRepository() {
        return userRepository.findAll();
    }

    public UserResponseDto getUserById(Long id) {
        User user = getUserFromRepository(id);
        return mapUserToDto(user);
    }

    public List<UserResponseDto> getUsersByUsername(String username) {

        List<User> users = userRepository.findUsersByUsername(username);

        return mapUsersToDto(users);
    }

    private UserResponseDto mapUserToDto(User user) {

        return new UserResponseDto(user);
    }

    private User getUserFromRepository(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponseDto getUserByKeyCloakId(String keycloakId) {

        User user = userRepository.findUserByKeyCloakId(keycloakId)
                        .orElseThrow(() -> new UserNotFoundException(keycloakId));

        return mapUserToDto(user);
    }
}
