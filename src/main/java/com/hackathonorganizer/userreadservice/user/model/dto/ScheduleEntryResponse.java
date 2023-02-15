package com.hackathonorganizer.userreadservice.user.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ScheduleEntryResponse(

        Long id,

        String username,

        Long teamId,

        Long userId,

        Long hackathonId,

        String info,

        String entryColor,

        boolean isAvailable,

        @DateTimeFormat(pattern = "HH:mm dd-MM-YYYY")
        LocalDateTime sessionStart,

        @DateTimeFormat(pattern = "HH:mm dd-MM-YYYY")
        LocalDateTime sessionEnd
) {
}
