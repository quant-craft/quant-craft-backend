package com.quant.craft.backend.presentation.controller.user;


import com.quant.craft.backend.application.service.UserService;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import com.quant.craft.backend.presentation.controller.user.dto.UserResponse;
import com.quant.craft.backend.presentation.controller.user.dto.UserStrategyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> readUserProfile(@RequiredLogin User user) {
        return ResponseEntity.ok(userService.findByUser(user.getId()));
    }

    @GetMapping("/strategies")
    public ResponseEntity<UserStrategyResponse> readUserStrategies(@RequiredLogin User user) {
        return ResponseEntity.ok(userService.findUserStrategies(user.getId()));
    }
}
