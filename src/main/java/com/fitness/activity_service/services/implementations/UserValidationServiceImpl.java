package com.fitness.activity_service.services.implementations;

import com.fitness.activity_service.services.contracts.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserValidationServiceImpl implements UserValidationService {
    private final WebClient userServiceWebClient;

    @Override
    public boolean isValidUser(UUID uuid) {
        Boolean response = userServiceWebClient.get().
                uri("/api/users/{uuid}/validate",uuid)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (response == null) {
            throw new IllegalStateException("UserServiceWebClient's response is null");
        }

        return response;
    }
}
