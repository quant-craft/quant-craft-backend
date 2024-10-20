package com.quant.craft.backend.presentation.controller.point;

import com.quant.craft.backend.application.service.PointService;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import com.quant.craft.backend.presentation.controller.point.dto.request.PointChargeRequest;
import com.quant.craft.backend.presentation.controller.point.dto.response.PointTxnsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/points")
public class PointController {

  private final PointService service;

  @PostMapping("/charge")
  public ResponseEntity<Void> charge(@RequiredLogin User user, @RequestBody PointChargeRequest request) {
    service.charge(user, request);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/txns")
  public ResponseEntity<PointTxnsResponse> viewPointTxns(@RequiredLogin User user) {
    return ResponseEntity.ok(service.findPointTxns(user));
  }
}
