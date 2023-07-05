package com.hackathonorganizer.userreadservice.user.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ScheduleEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Team id must not be null")
    private Long teamId;

    @NotNull(message = "Hackathon id must not be null")
    private Long hackathonId;

    private String info;

    private String entryColor;

    @Builder.Default
    private boolean isAvailable = true;

    @NotNull(message = "Session start must not be null")
    @DateTimeFormat(pattern = "HH:mm dd-MM-YYYY")
    private OffsetDateTime sessionStart;

    @NotNull(message = "Session end must not be null")
    @DateTimeFormat(pattern = "HH:mm dd-MM-YYYY")
    private OffsetDateTime sessionEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
