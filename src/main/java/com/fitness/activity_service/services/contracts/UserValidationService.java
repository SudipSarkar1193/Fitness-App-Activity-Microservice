package com.fitness.activity_service.services.contracts;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserValidationService {
    boolean isValidUser(UUID uuid);
}
