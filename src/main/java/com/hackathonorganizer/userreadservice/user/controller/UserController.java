package com.hackathonorganizer.userreadservice.user.controller;

import com.hackathonorganizer.userreadservice.user.model.dto.ScheduleEntryResponse;
import com.hackathonorganizer.userreadservice.user.model.dto.UserMembershipResponse;
import com.hackathonorganizer.userreadservice.user.model.dto.UserResponseDto;
import com.hackathonorganizer.userreadservice.user.service.UserService;
import com.hackathonorganizer.userreadservice.user.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/read/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    UserResponseDto getUserById(@PathVariable("id") Long id) {

        return UserMapper.mapUserToDto(userService.getUserById(id));
    }

    @GetMapping("/keycloak/{keycloakId}")
    UserResponseDto getUserByKeycloakId( @PathVariable("keycloakId") String keycloakId) {

        return userService.getUserByKeyCloakId(keycloakId);
    }
    
    @GetMapping
    Page<UserResponseDto> getHackathonUsersByUsername(
            @RequestParam("username") String username,
            @RequestParam("hackathonId") Long hackathonId, Pageable pageable) {

        return userService.getHackathonParticipantsByUsername(username, hackathonId, pageable);
    }

    // TODO add controller to retrieve schedule which date >= today && asc

    @GetMapping("/{userId}/schedule")
    @RolesAllowed({"MENTOR"})
    Set<ScheduleEntryResponse> getUserSchedule(@PathVariable("userId") Long userId,
            @RequestParam("hackathonId") Long hackathonId) {
        return userService.getUserScheduleEntries(userId, hackathonId);
    }

    @GetMapping("/schedule")
    @RolesAllowed({"USER","MENTOR","JURY","ORGANIZER"})
    Set<ScheduleEntryResponse> getAllUsersSchedule(@RequestParam("hackathonId") Long hackathonId) {
        return userService.getAllScheduleEntriesByHackathonId(hackathonId);
    }

    @GetMapping("/membership")
    @RolesAllowed({"USER"})
    List<UserResponseDto> getMembersByTeamId(@RequestParam("teamId") Long teamId) {
        return userService.getMembersByTeamId(teamId);
    }

    @GetMapping("/{userId}/membership")
    @RolesAllowed({"USER"})
    UserMembershipResponse getUserMembership(@PathVariable("userId") Long userId) {
        return userService.getUserMembershipDetails(userId);
    }

    @PostMapping("/hackathon-participants")
    Page<UserResponseDto> getHackathonParticipants(@RequestBody List<Long> usersIds, Pageable pageable) {
        return userService.getHackathonParticipants(usersIds, pageable);
    }
}
