package com.fitness.activity_service.services.implementations;

import com.fitness.activity_service.dtos.ActivityRequestDTO;
import com.fitness.activity_service.dtos.ActivityResponseDTO;
import com.fitness.activity_service.dtos.ResponseDTO;
import com.fitness.activity_service.models.Activity;
import com.fitness.activity_service.repository.ActivityRepository;
import com.fitness.activity_service.services.contracts.ActivityService;
import com.fitness.activity_service.services.contracts.UserValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final KafkaTemplate<String,Activity> activityKafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;;


    @Override
    public ResponseDTO createActivity(ActivityRequestDTO activityRequest) {

        log.info("Validating createActivity request for user UUID: {}", activityRequest.getUserUuid());

        if(!userValidationService.isValidUser(activityRequest.getUserUuid())){
            throw new IllegalStateException("User is not valid");
        }

        Activity activity = Activity.builder().
                activityUuid(UUID.randomUUID())
                .userUuid(activityRequest.getUserUuid())
                .name(activityRequest.getName())
                .type(activityRequest.getType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .startTime(activityRequest.getStartTime())
                .endTime(activityRequest.getEndTime())
                .additionalData(activityRequest.getAdditionalData())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        try {
            activityKafkaTemplate.send(
                    topicName,
                    String.valueOf(savedActivity.getActivityUuid()),
                    savedActivity
            );
        } catch (Exception e) {
            throw new RuntimeException("Kafka error from ActivityServiceImpl : "+e);
        }

        ActivityResponseDTO activityResponseDTO = mapToResponseDTO(savedActivity);
        return new ResponseDTO(activityResponseDTO,"Activity created successfully");

    }

    private ActivityResponseDTO mapToResponseDTO(Activity savedActivity) {
        ActivityResponseDTO responseDTO = new ActivityResponseDTO();
        responseDTO.setActivityUuid(savedActivity.getActivityUuid());
        responseDTO.setUserUuid(savedActivity.getUserUuid());
        responseDTO.setName(savedActivity.getName());
        responseDTO.setType(savedActivity.getType());
        responseDTO.setDuration(savedActivity.getDuration());
        responseDTO.setCaloriesBurned(savedActivity.getCaloriesBurned());
        responseDTO.setStartTime(savedActivity.getStartTime());
        responseDTO.setEndTime(savedActivity.getEndTime());
        responseDTO.setAdditionalData(savedActivity.getAdditionalData());
        responseDTO.setCreatedAt(savedActivity.getCreatedAt());
        responseDTO.setUpdatedAt(savedActivity.getUpdatedAt());
        return responseDTO;
    }
}
