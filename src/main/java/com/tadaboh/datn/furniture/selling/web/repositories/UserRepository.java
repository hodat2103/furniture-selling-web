package com.tadaboh.datn.furniture.selling.web.repositories;

import com.tadaboh.datn.furniture.selling.web.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);
    Optional<User> findByEmail(String email);
    Boolean existsByPhone(String phone);
    Boolean existsByEmail(String email);
}
