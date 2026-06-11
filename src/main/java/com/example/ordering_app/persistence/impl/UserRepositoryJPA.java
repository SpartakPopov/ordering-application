package com.example.ordering_app.persistence.impl;

import com.example.ordering_app.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryJPA extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
}
