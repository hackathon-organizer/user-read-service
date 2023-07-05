package com.hackathonorganizer.userreadservice.user.service;

import com.hackathonorganizer.userreadservice.user.creator.TestDataUtils;
import com.hackathonorganizer.userreadservice.user.model.ScheduleEntry;
import com.hackathonorganizer.userreadservice.user.model.Tag;
import com.hackathonorganizer.userreadservice.user.model.User;
import com.hackathonorganizer.userreadservice.user.model.dto.ScheduleEntryResponse;
import com.hackathonorganizer.userreadservice.user.model.dto.UserResponse;
import com.hackathonorganizer.userreadservice.user.repository.ScheduleEntryRepository;
import com.hackathonorganizer.userreadservice.user.repository.TagRepository;
import com.hackathonorganizer.userreadservice.user.repository.UserRepository;

import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Principal principal;

    @Mock
    private ScheduleEntryRepository scheduleEntryRepository;

    @Mock
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        principal = new UserPrincipal(mockUser.getKeyCloakId());
    }

    private User mockUser = TestDataUtils.buildMockUser();

    @Test
    void shouldReturnUserById() {
        //given
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockUser));

        //when
        UserResponse result = userService.getUserById(mockUser.getId());

        //then
        verify(userRepository).findById(anyLong());
        assertThat(result.id()).isEqualTo(mockUser.getId());
        assertThat(result.keyCloakId()).isEqualTo(mockUser.getKeyCloakId());
        assertThat(result.tags().size()).isEqualTo(0);
    }

    @Test
    void shouldReturnUserByKeycloakId() {
        //given
        when(userRepository.findUserByKeyCloakId(anyString())).thenReturn(Optional.of(mockUser));

        //when
        UserResponse result = userService.getUserByKeyCloakId("id");

        //then
        verify(userRepository).findUserByKeyCloakId("id");
        assertThat(result.id()).isEqualTo(mockUser.getId());
        assertThat(result.keyCloakId()).isEqualTo(mockUser.getKeyCloakId());
        assertThat(result.tags().size()).isEqualTo(0);
    }

    @Test
    void shouldReturnUsersByUsername() {
        //given

        PageImpl<User> page = new PageImpl<>(List.of(mockUser));

        when(userRepository.findByUsernameAndCurrentHackathonId(anyString(),
                anyLong(), any(Pageable.class))).thenReturn(page);

        //when
        Page<UserResponse> result =
                userService.getHackathonParticipantsByUsername("username", mockUser.getCurrentHackathonId(), Pageable.ofSize(5));

        //then
        verify(userRepository).findByUsernameAndCurrentHackathonId(anyString(), anyLong(), any(Pageable.class));
        assertThat(result.getContent().get(0).id()).isEqualTo(mockUser.getId());
        assertThat(result.getContent().get(0).keyCloakId()).isEqualTo(mockUser.getKeyCloakId());
        assertThat(result.getContent().get(0).username()).isEqualTo(mockUser.getUsername());
        assertThat(result.getContent().size()).isEqualTo(1);
    }

    @Test
    void shouldReturnUserSchedule() {
        //given

        ScheduleEntry scheduleEntry = TestDataUtils.buildScheduleEntry(mockUser);

        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockUser));
        when(scheduleEntryRepository.findByUserIdAndHackathonId(anyLong(), anyLong())).thenReturn(
                Set.of(scheduleEntry)
        );

        //when
        Set<ScheduleEntryResponse> result =
                userService.getUserScheduleEntries(mockUser.getId(), mockUser.getCurrentHackathonId(), principal);

        //then
        verify(userRepository).findById(anyLong());
        verify(scheduleEntryRepository).findByUserIdAndHackathonId(anyLong(), anyLong());
        assertThat(result.stream().findFirst().get().sessionStart()).isEqualTo(scheduleEntry.getSessionStart());
        assertThat(result.stream().findFirst().get().sessionEnd()).isEqualTo(scheduleEntry.getSessionEnd());
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnUserHackathonSchedule() {
        //given
        ScheduleEntry scheduleEntry = TestDataUtils.buildScheduleEntry(mockUser);

        when(scheduleEntryRepository.getAllScheduleEntriesByHackathonId(anyLong())).thenReturn(
                Set.of(scheduleEntry)
        );

        //when
        Set<ScheduleEntryResponse> result = userService.getHackathonUsersSchedule(mockUser.getCurrentHackathonId());

        //then
        verify(scheduleEntryRepository).getAllScheduleEntriesByHackathonId(anyLong());
        assertThat(result.stream().findFirst().get().sessionStart()).isEqualTo(scheduleEntry.getSessionStart());
        assertThat(result.stream().findFirst().get().sessionEnd()).isEqualTo(scheduleEntry.getSessionEnd());
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnTeamMembers() {
        //given
        when(userRepository.findTeamMembersByTeamId(anyLong())).thenReturn(List.of(mockUser));

        //when
        List<UserResponse> result = userService.getMembersByTeamId(mockUser.getCurrentTeamId());

        //then
       verify(userRepository).findTeamMembersByTeamId(anyLong());
       assertThat(result.size()).isEqualTo(1);
       assertThat(result.get(0).username()).isEqualTo(mockUser.getUsername());
       assertThat(result.get(0).keyCloakId()).isEqualTo(mockUser.getKeyCloakId());
    }

    @Test
    void shouldReturnTags() {
        //given
        List<Tag> tags = List.of(
                new Tag(1L, "Java"),
                new Tag(1L, "Python")
        );

        when(tagRepository.findAll()).thenReturn(tags);

        //when
        List<Tag> result = userService.getAllTags();

        //then
        verify(tagRepository).findAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(1).getName()).isEqualTo(tags.get(1).getName());
    }
}