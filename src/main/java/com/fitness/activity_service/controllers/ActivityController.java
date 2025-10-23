package com.fitness.activity_service.controllers;

import com.fitness.activity_service.dtos.ActivityRequestDTO;
import com.fitness.activity_service.dtos.ResponseDTO;
import com.fitness.activity_service.services.contracts.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    @PostMapping
    public ResponseEntity<ResponseDTO> createActivity(@RequestBody ActivityRequestDTO requestDTO) {
        if(requestDTO.getName() == null || requestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Invalid name supplied");
        }

        ResponseDTO responseDTO = activityService.createActivity(requestDTO);

        return ResponseEntity.ok(responseDTO);

    }
}
