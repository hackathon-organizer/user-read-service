package com.hackathonorganizer.userreadservice.user.controller;

import com.hackathonorganizer.userreadservice.user.IntegrationBaseClass;
import com.hackathonorganizer.userreadservice.user.creator.Role;
import com.hackathonorganizer.userreadservice.user.creator.TestDataUtils;
import com.hackathonorganizer.userreadservice.user.model.User;
import com.hackathonorganizer.userreadservice.user.repository.ScheduleEntryRepository;
import com.hackathonorganizer.userreadservice.user.repository.TagRepository;
import com.hackathonorganizer.userreadservice.user.repository.UserRepository;
import com.nimbusds.jwt.JWTParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest extends IntegrationBaseClass {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleEntryRepository scheduleEntryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TestDataUtils testDataUtils;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldGetUserById() throws Exception {
        //given

        User user = testDataUtils.createUser();

        //when
        ResultActions resultActions =  mockMvc.perform(getJsonRequest("/" + user.getId()));
        //then
        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.keyCloakId").value(user.getKeyCloakId()))
                .andExpect(jsonPath("$.tags").exists());
    }

    @Test
    void shouldReturnUserByKeycloakId() throws Exception{
        //given

        String token = getJaneDoeBearer(Role.USER);
        String keycloakId = JWTParser.parse(token).getJWTClaimsSet().getSubject();
        User user = testDataUtils.createUserWithKeycloakId(keycloakId);

        //when
        ResultActions resultActions =  mockMvc.perform(getJsonRequest("/keycloak", user.getKeyCloakId()));
        //then
        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.keyCloakId").value(user.getKeyCloakId()))
                .andExpect(jsonPath("$.tags").exists());
    }

    @Test
    void shouldReturnUsersByUsername() throws Exception {
        //given

        User user = testDataUtils.createUser();
        User user2 = testDataUtils.createUser();

        //when

        ResultActions resultActions =
                mockMvc.perform(getJsonRequest("?username=user&hackathonId=" + user.getCurrentHackathonId()));

        //then

        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].username").value(user.getUsername()))
                .andExpect(jsonPath("$.content[1].username").value(user2.getUsername()))
                .andExpect(jsonPath("$.content[0].currentHackathonId").value(user.getCurrentHackathonId()))
                .andExpect(jsonPath("$.content[1].currentHackathonId").value(user2.getCurrentHackathonId()));
    }

    @Test
    void shouldNotReturnGetUserSchedule() throws Exception {
        //given

        String token = getJaneDoeBearer(Role.USER);
        String keycloakId = JWTParser.parse(token).getJWTClaimsSet().getSubject();
        User user = testDataUtils.createUserWithKeycloakId(keycloakId);

        //when
        ResultActions resultActions =
                mockMvc.perform(getAuthorizedJsonRequest( token,
                        "/" + user.getId(),
                        "schedule?hackathonId=" + user.getCurrentHackathonId()));
        //then
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnUserSchedule() throws Exception {
        //given

        String token = getJaneDoeBearer(Role.ORGANIZER);
        String keycloakId = JWTParser.parse(token).getJWTClaimsSet().getSubject();
        User user = testDataUtils.createUserWithKeycloakId(keycloakId);

        testDataUtils.createScheduleEntry(user);

        //when
        ResultActions resultActions =
                mockMvc.perform(getAuthorizedJsonRequest(token,
                        "/" + user.getId(),
                        "schedule?hackathonId=" + user.getCurrentHackathonId()));
        //then
        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].username").value(user.getUsername()));
    }

    @Test
    void shouldReturnHackathonSchedule() throws Exception {
        //given
        User user = testDataUtils.createUser();

        testDataUtils.createScheduleEntry(user);
        testDataUtils.createScheduleEntry(user);

        //when
        ResultActions resultActions =
                mockMvc.perform(getJsonRequest("/schedule?hackathonId=" + user.getCurrentHackathonId()));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnUserMembership() throws Exception {
        //given

        String token = getJaneDoeBearer(Role.USER);
        String keycloakId = JWTParser.parse(token).getJWTClaimsSet().getSubject();
        User user = testDataUtils.createUserWithKeycloakId(keycloakId);

        //when
        ResultActions resultActions =
                mockMvc.perform(getAuthorizedJsonRequest(
                        token,
                        "/membership?teamId=" + user.getCurrentTeamId()));

        //then
        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$[0].currentHackathonId").value(user.getCurrentHackathonId()))
                .andExpect(jsonPath("$[0].currentTeamId").value(user.getCurrentTeamId()));
    }

    @Test
    void shouldReturnParticipants() throws Exception {
        //given

        String token = getJaneDoeBearer(Role.USER);
        String keycloakId = JWTParser.parse(token).getJWTClaimsSet().getSubject();
        User user = testDataUtils.createUserWithKeycloakId(keycloakId);

        String token2 = getJaneDoeBearer(Role.ORGANIZER);
        String keycloakId2 = JWTParser.parse(token2).getJWTClaimsSet().getSubject();
        User user2 = testDataUtils.createUserWithKeycloakId(keycloakId2);

        String token3 = getJaneDoeBearer(Role.TEAM_OWNER);
        String keycloakId3 = JWTParser.parse(token3).getJWTClaimsSet().getSubject();
        User user3 = testDataUtils.createUserWithKeycloakId(keycloakId3);


        List<Long> userIds = List.of(user.getId(), user2.getId(), user3.getId());

        //when
        ResultActions resultActions =
                mockMvc.perform(postJsonRequest(userIds, token3, "/hackathon-participants"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(userIds.size()));
    }

    @Test
    void shouldReturnAllTags() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(getJsonRequest("/tags"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(tagRepository.findAll().size()));
    }
}