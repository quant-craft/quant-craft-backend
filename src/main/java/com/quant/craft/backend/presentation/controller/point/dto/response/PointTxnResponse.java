package com.quant.craft.backend.presentation.controller.point.dto.response;

import com.quant.craft.backend.domain.point.PointTxn;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PointTxnResponse {

  private Long id;

  private Long userId;

  private Long point;

  private String status;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public static PointTxnResponse from(PointTxn pointTxn) {
    return new PointTxnResponse(
        pointTxn.getId(),
        pointTxn.getUser().getId(),
        pointTxn.getPoint(),
        pointTxn.getStatus().name(),
        pointTxn.getCreatedAt(),
        pointTxn.getUpdatedAt()
    );
  }
}
