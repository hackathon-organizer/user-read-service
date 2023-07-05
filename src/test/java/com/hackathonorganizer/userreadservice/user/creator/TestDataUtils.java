package com.hackathonorganizer.userreadservice.user.creator;

import com.hackathonorganizer.userreadservice.user.model.ScheduleEntry;
import com.hackathonorganizer.userreadservice.user.model.User;
import com.hackathonorganizer.userreadservice.user.model.dto.UserResponse;
import com.hackathonorganizer.userreadservice.user.repository.ScheduleEntryRepository;
import com.hackathonorganizer.userreadservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestDataUtils {

    private final UserRepository userRepository;
    private final ScheduleEntryRepository scheduleEntryRepository;

    public static long HACKATHON_ID = 15L;
    public static long TEAM_ID = 5L;

    public User createUser() {
        UUID uuid = UUID.randomUUID();
        return userRepository.save(User.builder()
                .id(null)
                .username("user" + uuid)
                .keyCloakId(uuid.toString())
                .githubProfileUrl("USER_GITHUB")
                .profilePictureUrl("USER_PICTURE")
                .currentTeamId(TEAM_ID)
                .currentHackathonId(HACKATHON_ID)
                .blocked(false)
                .tags(new HashSet<>())
                .build());
    }

    public User createUserWithKeycloakId(String keycloakId) {
        UUID uuid = UUID.randomUUID();
        return userRepository.save(User.builder()
                .id(null)
                .username(uuid.toString())
                .keyCloakId(keycloakId)
                .githubProfileUrl("USER_GITHUB")
                .profilePictureUrl("USER_PICTURE")
                .currentTeamId(TEAM_ID)
                .currentHackathonId(HACKATHON_ID)
                .tags(new HashSet<>())
                .build());
    }

    public static User buildMockUser() {
        return User.builder()
                .id(1L)
                .username("username")
                .keyCloakId("id")
                .blocked(false)
                .currentHackathonId(HACKATHON_ID)
                .currentTeamId(TEAM_ID)
                .scheduleEntries(Set.of())
                .tags(new HashSet<>())
                .build();
    }

    public ScheduleEntry createScheduleEntry(User user) {
        return scheduleEntryRepository.save(new ScheduleEntry(
                null,
                TEAM_ID,
                HACKATHON_ID,
                "info",
                "red",
                true,
                OffsetDateTime.of(2123, 10, 10, 12, 0, 0, 0, ZoneOffset.UTC),
                OffsetDateTime.of(2123, 10, 10, 16, 0, 0, 0, ZoneOffset.UTC),
                user
        ));
    }

    public static UserResponse buildUserResponseDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getDescription(),
                user.getKeyCloakId(),
                user.getCurrentHackathonId(),
                user.getCurrentTeamId(),
                user.getTags()
        );
    }

    public static ScheduleEntry buildScheduleEntry(User user) {
        return new ScheduleEntry(1L,
                TEAM_ID,
                HACKATHON_ID,
                "info",
                "red",
                true,
                OffsetDateTime.of(2123, 10, 10, 12, 0, 0, 0, ZoneOffset.UTC),
                OffsetDateTime.of(2123, 10, 10, 16, 0, 0, 0, ZoneOffset.UTC),
                user);
    }
}
