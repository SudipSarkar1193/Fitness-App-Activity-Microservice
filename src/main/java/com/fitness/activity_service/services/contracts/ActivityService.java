package com.fitness.activity_service.services.contracts;

import com.fitness.activity_service.dtos.ActivityRequestDTO;
import com.fitness.activity_service.dtos.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ActivityService {
    ResponseDTO createActivity(ActivityRequestDTO activityRequestDTO);
}
