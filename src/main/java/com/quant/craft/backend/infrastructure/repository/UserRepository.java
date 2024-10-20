package com.quant.craft.backend.infrastructure.repository;


import com.quant.craft.backend.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByOauthId(String oauthId);

  Optional<User> findByRefreshToken(String refreshToken);
}
