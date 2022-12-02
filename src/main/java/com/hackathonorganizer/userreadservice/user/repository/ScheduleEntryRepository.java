package com.hackathonorganizer.userreadservice.user.repository;

import com.hackathonorganizer.userreadservice.user.model.ScheduleEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ScheduleEntryRepository extends JpaRepository<ScheduleEntry, Long> {

    Set<ScheduleEntry> findByUserIdAndHackathonId(Long userId, Long hackathonId);

    @Query("SELECT s FROM ScheduleEntry s WHERE s.hackathonId = :hackathonId")
    Set<ScheduleEntry> getAllScheduleEntriesByHackathonId(Long hackathonId);
}