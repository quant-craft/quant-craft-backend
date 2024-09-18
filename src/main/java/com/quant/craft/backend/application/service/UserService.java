package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.infrastructure.repository.UserRepository;
import com.quant.craft.backend.presentation.controller.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponse findUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found User. id: " + userId));
        return UserResponse.from(user);
    }
}
