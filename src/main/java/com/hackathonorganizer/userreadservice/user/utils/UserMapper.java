package com.hackathonorganizer.userreadservice.user.utils;

import com.hackathonorganizer.userreadservice.user.model.User;
import com.hackathonorganizer.userreadservice.user.model.dto.UserResponseDto;
import lombok.experimental.UtilityClass;

import java.util.List;

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

}
