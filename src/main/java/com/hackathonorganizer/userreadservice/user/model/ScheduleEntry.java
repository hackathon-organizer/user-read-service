package com.hackathonorganizer.userreadservice.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@JsonIgnoreProperties(value = {"user"})
public class ScheduleEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    Long teamId;

    @NotNull
    Long hackathonId;

    String info;

    String entryColor;

    @NotNull
    LocalDateTime sessionStart;

    @NotNull
    LocalDateTime sessionEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    User user;
}
