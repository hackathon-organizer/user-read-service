package com.hackathonorganizer.userreadservice.user.service;


import com.hackathonorganizer.userreadservice.exception.UserException;
import com.hackathonorganizer.userreadservice.user.model.dto.UserResponseDto;
import com.hackathonorganizer.userreadservice.user.model.ScheduleEntry;
import com.hackathonorganizer.userreadservice.user.model.User;
import com.hackathonorganizer.userreadservice.user.repository.UserRepository;
import com.hackathonorganizer.userreadservice.user.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDto> getUsersByUsername(String username) {

        List<User> users = userRepository.findUsersByUsername(username);

        return UserMapper.mapUsersToDto(users);
    }

    public UserResponseDto getUserByKeyCloakId(String keycloakId) {

        User user =  userRepository.findUserByKeyCloakId(keycloakId).orElseThrow(() ->
                new UserException(String.format("User with id: %s not found", keycloakId),
                HttpStatus.NOT_FOUND));

        return UserMapper.mapUserToDto(user);
    }

    public Set<ScheduleEntry> getAllScheduleEntriesByHackathonId(Long hackathonId) {
        return userRepository.getAllScheduleEntriesByHackathonId(hackathonId);
    }

    public Set<ScheduleEntry> getUserScheduleEntriesByUserId(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserException(String.format("User with id: %d not found", userId),
                HttpStatus.NOT_FOUND));

        return userRepository.getUserScheduleEntriesByUserId(userId);
    }

    public UserResponseDto getUserById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserException(String.format("User with id: %d not found", userId),
                HttpStatus.NOT_FOUND));

        return UserMapper.mapUserToDto(user);
    }
}
