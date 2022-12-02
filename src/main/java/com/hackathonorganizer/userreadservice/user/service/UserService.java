package com.hackathonorganizer.userreadservice.user.service;


import com.hackathonorganizer.userreadservice.exception.UserException;
import com.hackathonorganizer.userreadservice.user.model.dto.ScheduleEntryResponse;
import com.hackathonorganizer.userreadservice.user.model.dto.UserMembershipResponse;
import com.hackathonorganizer.userreadservice.user.model.dto.UserResponseDto;
import com.hackathonorganizer.userreadservice.user.model.User;
import com.hackathonorganizer.userreadservice.user.repository.ScheduleEntryRepository;
import com.hackathonorganizer.userreadservice.user.repository.UserRepository;
import com.hackathonorganizer.userreadservice.user.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ScheduleEntryRepository scheduleEntryRepository;

    public UserResponseDto getUserByKeyCloakId(String keycloakId) {

        User user =  userRepository.findUserByKeyCloakId(keycloakId).orElseThrow(() ->
                new UserException(String.format("User with id: %s not found", keycloakId),
                HttpStatus.NOT_FOUND));

        return UserMapper.mapUserToDto(user);
    }

    public Set<ScheduleEntryResponse> getAllScheduleEntriesByHackathonId(Long hackathonId) {

        return  UserMapper.mapToScheduleEntryResponses(
                scheduleEntryRepository.getAllScheduleEntriesByHackathonId(hackathonId));
    }

    public Set<ScheduleEntryResponse> getUserScheduleEntries(Long userId, Long hackathonId) {

        return UserMapper.mapToScheduleEntryResponses(
                scheduleEntryRepository.findByUserIdAndHackathonId(userId, hackathonId));
    }

    public User getUserById(Long userId) {

        return userRepository.findById(userId).orElseThrow(() ->
                new UserException(String.format("User with id: %d not found", userId),
                HttpStatus.NOT_FOUND));
    }

    public List<UserResponseDto> getMembersByTeamId(Long teamId) {

        return userRepository.findTeamMembersByTeamId(teamId)
                .stream().map(UserMapper::mapUserToDto).toList();
    }

    public UserMembershipResponse getUserMembershipDetails(Long userId) {

        User user = getUserById(userId);

        return new UserMembershipResponse(user.getId(),
                user.getCurrentHackathonId(), user.getCurrentTeamId());
    }

    public Page<UserResponseDto> getHackathonParticipants(List<Long> usersIds, Pageable pageable) {

        Page<User> usersPage = userRepository.findAllByIdIn(usersIds, pageable);

        List<UserResponseDto> usersResponse = usersPage.getContent()
                .stream().map(UserMapper::mapUserToDto).toList();

        return new PageImpl<>(usersResponse, pageable, usersPage.getTotalElements());
    }

    public Page<UserResponseDto> getHackathonParticipantsByUsername(String username, Long hackathonId, Pageable pageable) {

        Page<User> usersPage =
                userRepository.findByUsernameAndCurrentHackathonId(username, hackathonId, pageable);

        List<UserResponseDto> usersResponse = usersPage.getContent()
                .stream().map(UserMapper::mapUserToDto).toList();

        return new PageImpl<>(usersResponse, pageable, usersPage.getTotalElements());
    }
}
