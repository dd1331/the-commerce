package com.example.thecommerce.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByIdentifier(String identifier);


    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    Optional<UserEntity> findOneByIdentifier(String identifier);
}
