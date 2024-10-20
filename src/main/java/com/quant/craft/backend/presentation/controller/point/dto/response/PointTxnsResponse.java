package com.quant.craft.backend.presentation.controller.point.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PointTxnsResponse {

  private List<PointTxnResponse> pointTxns;
}
