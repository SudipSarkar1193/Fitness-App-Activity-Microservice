package com.fitness.activity_service.dtos;

import com.fitness.activity_service.models.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
public class ActivityRequestDTO {

    private UUID userUuid;
    private String name;
    private ActivityType type;
    private long duration; // in seconds
    private long caloriesBurned;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Map<String, Object> additionalData;
}
