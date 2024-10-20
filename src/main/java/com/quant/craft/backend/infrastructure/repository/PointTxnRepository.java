package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.point.PointTxn;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTxnRepository extends JpaRepository<PointTxn, Long> {

  List<PointTxn> findAllByUserId(Long userId);
}
