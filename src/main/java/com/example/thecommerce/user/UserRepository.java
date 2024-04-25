package com.example.thecommerce.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByIdentifier(String identifier);


    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);
}
