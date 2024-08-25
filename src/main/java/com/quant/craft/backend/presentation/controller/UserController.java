package com.quant.craft.backend.presentation.controller;


import com.quant.craft.backend.application.service.UserService;
import com.quant.craft.backend.domain.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import com.quant.craft.backend.presentation.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> viewUserProfile(@RequiredLogin User user) {
        return ResponseEntity.ok(userService.findUser(user.getId()));
    }
}
