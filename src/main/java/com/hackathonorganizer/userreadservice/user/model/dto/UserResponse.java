package com.hackathonorganizer.userreadservice.user.model.dto;


import com.hackathonorganizer.userreadservice.user.model.Tag;

import java.util.Set;

public record UserResponse(

        Long id,
        String username,
        String description,
        String keyCloakId,
        Long currentHackathonId,
        Long currentTeamId,
        Set<Tag> tags
) {
}
