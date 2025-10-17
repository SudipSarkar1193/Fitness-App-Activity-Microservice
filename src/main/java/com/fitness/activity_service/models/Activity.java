package com.fitness.activity_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Document(collection = "activities")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    // ❗ Use String or ObjectId for MongoDB _id field (not primitive long)
    @Id
    private String id;

    private UUID activityUuid;
    private UUID userUuid;

    private String name;
    private ActivityType type;
    private long duration;       // seconds
    private long caloriesBurned;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Field("additional_data")
    private Map<String, Object> additionalData;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
