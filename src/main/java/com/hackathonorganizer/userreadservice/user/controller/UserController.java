package com.hackathonorganizer.userreadservice.user.controller;

import com.hackathonorganizer.userreadservice.user.dto.UserResponseDto;
import com.hackathonorganizer.userreadservice.user.model.ScheduleEntry;
import com.hackathonorganizer.userreadservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/read/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    UserResponseDto getUserById(@PathVariable("id") Long id) {

        return userService.getUserById(id);
    }

    @GetMapping("/kc/{keycloakId}")
    UserResponseDto getUserByKeycloakId(@PathVariable("keycloakId") String keycloakId) {

        return userService.getUserByKeyCloakId(keycloakId);
    }
    
    @GetMapping
    List<UserResponseDto> getUserByUsername(@RequestParam("username") String username) {

        return userService.getUsersByUsername(username);
    }

    @GetMapping("/{userId}/schedule")
    Set<ScheduleEntry> getUserSchedule(@PathVariable("userId") Long userId) {
        return userService.getUserScheduleEntriesByUserId(userId);
    }

    @GetMapping("/schedule")
    Set<ScheduleEntry> getAllUsersSchedule(@RequestParam("hackathonId") Long hackathonId) {
        return userService.getAllScheduleEntriesByHackathonId(hackathonId);
    }
}
