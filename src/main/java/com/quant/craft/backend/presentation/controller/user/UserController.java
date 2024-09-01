package com.quant.craft.backend.presentation.controller.user;


import com.quant.craft.backend.application.service.PointService;
import com.quant.craft.backend.application.service.UserService;
import com.quant.craft.backend.domain.point.PointTxn;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import com.quant.craft.backend.presentation.controller.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PointService pointService;

    @GetMapping
    public ResponseEntity<UserResponse> viewUserProfile(@RequiredLogin User user) {
        return ResponseEntity.ok(userService.findUser(user.getId()));
    }

    @GetMapping("/point-txns")
    public ResponseEntity<List<PointTxn>> viewUserPointTxns(@RequiredLogin User user) {
        return ResponseEntity.ok(pointService.findPointTxns(user));
    }
}
