package com.hackathonorganizer.userreadservice.user.model.dto;

public record UserMembershipResponse(

        Long userId,
        Long currentHackathonId,
        Long currentTeamId
) {
}
