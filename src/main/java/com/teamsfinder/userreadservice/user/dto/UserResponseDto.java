package com.teamsfinder.userreadservice.user.dto;

import com.teamsfinder.userreadservice.tag.dto.TagResponseDto;
import com.teamsfinder.userreadservice.tag.model.Tag;
import com.teamsfinder.userreadservice.user.model.AccountType;
import com.teamsfinder.userreadservice.user.model.User;

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

        List<TagResponseDto> tags
) {

    public UserResponseDto(User user){
        this(
                user.getId(),
                user.getUsername(),
                user.getKeyCloakId(),
                user.getAccountType(),
                user.getGithubProfileUrl(),
                user.getProfilePictureUrl(),
                user.getCurrentHackathonId(),
                user.getCurrentTeamId(),
                user.isBlocked(),
                mapTagsToDto(user.getTags())
        );
    }

    private static List<TagResponseDto> mapTagsToDto(List<Tag> tags) {
        return tags.stream()
                .map(tag -> new TagResponseDto(tag))
                .toList();
    }
}
