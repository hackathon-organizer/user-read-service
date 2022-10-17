package com.hackathonorganizer.userreadservice.user.service;

import com.hackathonorganizer.userreadservice.user.dto.UserResponseDto;
import com.hackathonorganizer.userreadservice.user.exception.UserNotFoundException;
import com.hackathonorganizer.userreadservice.user.model.ScheduleEntry;
import com.hackathonorganizer.userreadservice.user.model.User;
import com.hackathonorganizer.userreadservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public Set<ScheduleEntry> getAllScheduleEntriesByHackathonId(Long hackathonId) {
        return userRepository.getAllScheduleEntriesByHackathonId(hackathonId);
    }

    public Set<ScheduleEntry> getUserScheduleEntriesByUserId(Long userId) {

        User user = userRepository.findById(userId).orElseThrow();


        return user.getScheduleEntries();
    }
}
