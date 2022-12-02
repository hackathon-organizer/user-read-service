package com.hackathonorganizer.userreadservice.user.model.dto;

import com.hackathonorganizer.userreadservice.user.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ScheduleEntryResponse(

    Long id,

    Long teamId,

    @NotNull
    Long userId,

    @NotNull
    Long hackathonId,

    String info,

    String entryColor,

    boolean isAvailable,

    @NotNull
    @DateTimeFormat(pattern = "HH:mm dd-MM-YYYY")
    LocalDateTime sessionStart,

    @NotNull
    @DateTimeFormat(pattern = "HH:mm dd-MM-YYYY")
    LocalDateTime sessionEnd
) {
}
