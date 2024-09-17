package com.quant.craft.backend.presentation.controller.point.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PointTxnsResponse {

    private List<PointTxnResponse> pointTxns;
}
