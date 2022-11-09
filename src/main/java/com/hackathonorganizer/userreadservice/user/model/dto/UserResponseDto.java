package com.hackathonorganizer.userreadservice.user.model.dto;


import com.hackathonorganizer.userreadservice.user.model.Tag;
import com.hackathonorganizer.userreadservice.user.model.AccountType;

import java.util.List;

public record UserResponseDto(

        Long id,

        String username,

        String keyCloakId,

        AccountType accountType,

        String githubProfileUrl,

        String profilePictureUrl,

        Long currentHackathonId,

        Long currentTeamId,

        boolean blocked,

        List<Tag> tags
) {
}
