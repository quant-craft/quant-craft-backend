package com.quant.craft.backend.infrastructure.repository;


import com.quant.craft.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
