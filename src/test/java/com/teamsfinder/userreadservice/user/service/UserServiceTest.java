package com.teamsfinder.userreadservice.user.service;

import com.hackathonorganizer.userreadservice.user.model.AccountType;
import com.hackathonorganizer.userreadservice.user.model.User;
import com.hackathonorganizer.userreadservice.user.model.dto.UserResponseDto;
import com.hackathonorganizer.userreadservice.user.repository.UserRepository;
import com.hackathonorganizer.userreadservice.user.service.UserService;
import com.teamsfinder.userreadservice.user.UnitBaseClass;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UserServiceTest extends UnitBaseClass {

    private static final String USER_KEYCLOAK_ID = "KEYCLOAK_ID";
    private static final String USER_GITHUB = "GITHUB";
    private static final String USER_PICTURE = "PICTURE";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService underTest;

    private User testUser = User.builder()
            .id(1L)
            .keyCloakId(USER_KEYCLOAK_ID)
            .accountType(AccountType.USER)
            .githubProfileUrl(USER_GITHUB)
            .profilePictureUrl(USER_PICTURE)
            .blocked(false)
            .tags(new HashSet<>())
            .build();

    @Test
    void getUserById() {
        //given
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testUser));

        //when
        UserResponseDto userDto = underTest.getUserById(1L);

        //then
        assertThat(userDto.id()).isEqualTo(1L);
        assertThat(userDto.keyCloakId()).isEqualTo(USER_KEYCLOAK_ID);
        assertThat(userDto.tags().size()).isEqualTo(0);
    }
}