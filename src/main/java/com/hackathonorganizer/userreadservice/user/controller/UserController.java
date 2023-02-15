package com.hackathonorganizer.userreadservice.user.controller;

import com.hackathonorganizer.userreadservice.user.model.Tag;
import com.hackathonorganizer.userreadservice.user.model.dto.ScheduleEntryResponse;
import com.hackathonorganizer.userreadservice.user.model.dto.UserResponseDto;
import com.hackathonorganizer.userreadservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/read/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable("id") Long id) {

        return userService.getUserById(id);
    }

    @GetMapping("/keycloak/{keycloakId}")
    public UserResponseDto getUserByKeycloakId(@PathVariable("keycloakId") String keycloakId) {

        return userService.getUserByKeyCloakId(keycloakId);
    }

    @GetMapping
    public Page<UserResponseDto> getHackathonUsersByUsername(@RequestParam("username") String username,
                                                             @RequestParam("hackathonId") Long hackathonId,
                                                             Pageable pageable) {

        return userService.getHackathonParticipantsByUsername(username, hackathonId, pageable);
    }

    @GetMapping("/{userId}/schedule")
    @RolesAllowed({"MENTOR", "ORGANIZER", "JURY"})
    Set<ScheduleEntryResponse> getUserSchedule(@PathVariable("userId") Long userId,
                                               @RequestParam("hackathonId") Long hackathonId,
                                               Principal principal) {

        return userService.getUserScheduleEntries(userId, hackathonId, principal);
    }

    @GetMapping("/schedule")
    Set<ScheduleEntryResponse> getHackathonUsersSchedule(@RequestParam("hackathonId") Long hackathonId) {

        return userService.getHackathonUsersSchedule(hackathonId);
    }

    @GetMapping("/membership")
    @RolesAllowed({"USER"})
    public List<UserResponseDto> getMembersByTeamId(@RequestParam("teamId") Long teamId) {

        return userService.getMembersByTeamId(teamId);
    }

    @PostMapping("/hackathon-participants")
    public Page<UserResponseDto> getHackathonParticipants(@RequestBody List<Long> usersIds, Pageable pageable) {

        return userService.getHackathonParticipants(usersIds, pageable);
    }

    @GetMapping("/tags")
    public List<Tag> getTags() {
        return userService.getAllTags();
    }
}
