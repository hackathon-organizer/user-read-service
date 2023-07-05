package com.hackathonorganizer.userreadservice.user.repository;

import com.hackathonorganizer.userreadservice.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByKeyCloakId(String keycloakId);

    @Query("SELECT u FROM User u WHERE u.currentTeamId = :teamId")
    List<User> findTeamMembersByTeamId(Long teamId);

    Page<User> findAllByIdIn(List<Long> usersIds, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.currentHackathonId = :hackathonId " +
            "AND u.username LIKE %:username%")
    Page<User> findByUsernameAndCurrentHackathonId(String username, Long hackathonId, Pageable pageable);

    @Query("SELECT u FROM User u JOIN FETCH u.scheduleEntries")
    Optional<User> findUserByIdWithScheduleEntries(Long userId);

    @Query("SELECT u FROM User u JOIN FETCH u.tags")
    Optional<User> findUserByIdWithTags(Long userId);
}
