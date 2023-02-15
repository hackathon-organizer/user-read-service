package com.hackathonorganizer.userreadservice.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "schedule_entry")
public class ScheduleEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long teamId;

    private Long hackathonId;

    private String info;

    private String entryColor;

    private boolean isAvailable;

    @DateTimeFormat(pattern = "HH:mm dd-MM-YYYY")
    private LocalDateTime sessionStart;

    @DateTimeFormat(pattern = "HH:mm dd-MM-YYYY")
    private LocalDateTime sessionEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
