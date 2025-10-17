package com.fitness.activity_service.services.implementations;

import com.fitness.activity_service.dtos.ActivityRequestDTO;
import com.fitness.activity_service.dtos.ActivityResponseDTO;
import com.fitness.activity_service.dtos.ResponseDTO;
import com.fitness.activity_service.models.Activity;
import com.fitness.activity_service.repository.ActivityRepository;
import com.fitness.activity_service.services.contracts.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    @Override
    public ResponseDTO createActivity(ActivityRequestDTO activityRequest) {
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
