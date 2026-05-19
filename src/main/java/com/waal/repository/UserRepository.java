package com.waal.repository;

import com.waal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByProviderAndProviderId(User.OAuthProvider provider, String providerId);
    Optional<User> findByEmail(String email);
}
