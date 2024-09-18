package com.quant.craft.backend.domain.point;

import com.quant.craft.backend.domain.BaseEntity;
import com.quant.craft.backend.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "point_txns")
public class PointTxn extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long point;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointTxnStatus status;
}
