package com.hackathonorganizer.userreadservice.user.utils;

import com.hackathonorganizer.userreadservice.user.model.ScheduleEntry;
import com.hackathonorganizer.userreadservice.user.model.User;
import com.hackathonorganizer.userreadservice.user.model.dto.ScheduleEntryResponse;
import com.hackathonorganizer.userreadservice.user.model.dto.UserResponseDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {


    public static UserResponseDto mapUserToDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getKeyCloakId(),
                user.getAccountType(),
                user.getGithubProfileUrl(),
                user.getProfilePictureUrl(),
                user.getCurrentHackathonId(),
                user.getCurrentTeamId(),
                user.isBlocked(),
                user.getTags()
        );
    }

    public static List<UserResponseDto> mapUsersToDto(List<User> user) {
        return user.stream().map(UserMapper::mapUserToDto).toList();
    }

    public static Set<ScheduleEntryResponse> mapToScheduleEntryResponses(Set<ScheduleEntry> scheduleEntries) {
        return scheduleEntries.stream().map(entry -> new ScheduleEntryResponse(
                entry.getId(),
                entry.getTeamId(),
                entry.getUser().getId(),
                entry.getHackathonId(),
                entry.getInfo(),
                entry.getEntryColor(),
                entry.isAvailable(),
                entry.getSessionStart(),
                entry.getSessionEnd()
        )).collect(Collectors.toSet());
    }

}
