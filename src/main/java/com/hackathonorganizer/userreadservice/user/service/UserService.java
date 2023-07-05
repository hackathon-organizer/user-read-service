package com.hackathonorganizer.userreadservice.user.service;


import com.hackathonorganizer.userreadservice.exception.UserException;
import com.hackathonorganizer.userreadservice.user.model.Tag;
import com.hackathonorganizer.userreadservice.user.model.User;
import com.hackathonorganizer.userreadservice.user.model.dto.ScheduleEntryResponse;
import com.hackathonorganizer.userreadservice.user.model.dto.UserResponse;
import com.hackathonorganizer.userreadservice.user.repository.ScheduleEntryRepository;
import com.hackathonorganizer.userreadservice.user.repository.TagRepository;
import com.hackathonorganizer.userreadservice.user.repository.UserRepository;
import com.hackathonorganizer.userreadservice.user.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ScheduleEntryRepository scheduleEntryRepository;
    private final TagRepository tagRepository;

    public UserResponse getUserByKeyCloakId(String keycloakId) {

        User user = userRepository.findUserByKeyCloakId(keycloakId).orElseThrow(() ->
                new UserException(String.format("User with id: %s not found", keycloakId), HttpStatus.NOT_FOUND));

        return UserMapper.mapToDto(user);
    }

    public Set<ScheduleEntryResponse> getHackathonUsersSchedule(Long hackathonId) {

        return UserMapper.mapToScheduleEntryResponseSet(
                scheduleEntryRepository.getAllScheduleEntriesByHackathonId(hackathonId));
    }

    public Set<ScheduleEntryResponse> getUserScheduleEntries(Long userId, Long hackathonId, Principal principal) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserException(String.format("User with id: %d not found", userId), HttpStatus.NOT_FOUND));

        if (verifyUser(user, principal)) {
            return UserMapper.mapToScheduleEntryResponseSet(
                    scheduleEntryRepository.findByUserIdAndHackathonId(userId, hackathonId));
        } else {
            return Set.of();
        }
    }

    public UserResponse getUserById(Long userId) {

        return UserMapper.mapToDto(userRepository.findById(userId).orElseThrow(() ->
                new UserException(String.format("User with id: %d not found", userId), HttpStatus.NOT_FOUND)));
    }

    public List<UserResponse> getMembersByTeamId(Long teamId) {

        return userRepository.findTeamMembersByTeamId(teamId).stream().map(UserMapper::mapToDto).toList();
    }

    public Page<UserResponse> getHackathonParticipants(List<Long> usersIds, Pageable pageable) {

        Page<User> usersPage = userRepository.findAllByIdIn(usersIds, pageable);

        List<UserResponse> usersResponse = usersPage.getContent().stream().map(UserMapper::mapToDto).toList();

        return new PageImpl<>(usersResponse, pageable, usersPage.getTotalElements());
    }

    public Page<UserResponse> getHackathonParticipantsByUsername(String username, Long hackathonId, Pageable pageable) {

        Page<User> usersPage = userRepository.findByUsernameAndCurrentHackathonId(username, hackathonId, pageable);

        List<UserResponse> usersResponse = usersPage.getContent().stream().map(UserMapper::mapToDto).toList();

        return new PageImpl<>(usersResponse, pageable, usersPage.getTotalElements());
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    private boolean verifyUser(User user, Principal principal) {

        if (user.getKeyCloakId().equals(principal.getName())) {
            return true;
        } else {
            log.info("User id: {} verification failed", user.getId());
            throw new UserException("User verification failed", HttpStatus.FORBIDDEN);
        }
    }
}
