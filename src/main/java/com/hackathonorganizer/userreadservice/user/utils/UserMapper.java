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


    public static UserResponseDto mapToDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getDescription(),
                user.getKeyCloakId(),
                user.getCurrentHackathonId(),
                user.getCurrentTeamId(),
                user.getTags()
        );
    }

    public static List<UserResponseDto> mapUsersToDto(List<User> user) {
        return user.stream().map(UserMapper::mapToDto).toList();
    }

    public static Set<ScheduleEntryResponse> mapToScheduleEntryResponseSet(Set<ScheduleEntry> scheduleEntries) {
        return scheduleEntries.stream().map(entry -> new ScheduleEntryResponse(
                entry.getId(),
                entry.getUser().getUsername(),
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
