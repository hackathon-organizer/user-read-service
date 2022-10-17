package com.hackathonorganizer.userreadservice.user.repository;

import com.hackathonorganizer.userreadservice.user.model.ScheduleEntry;
import com.hackathonorganizer.userreadservice.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User u WHERE u.username LIKE %:username%")
    List<User> findUsersByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.keyCloakId = :keycloakId")
    Optional<User> findUserByKeyCloakId(String keycloakId);

    @Query("SELECT s FROM ScheduleEntry s WHERE hackathonId = :hackathonId")
    Set<ScheduleEntry> getAllScheduleEntriesByHackathonId(Long hackathonId);

    @Query("SELECT s FROM ScheduleEntry s WHERE userId = :userId")
    Set<ScheduleEntry> getUserScheduleEntriesByUserId(Long userId);
}
