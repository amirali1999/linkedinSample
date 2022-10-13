package com.example.linkedinSample.repository;

import com.example.linkedinSample.entity.JwtBlacklist;
import com.example.linkedinSample.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Long> {
    Optional<JwtBlacklist> findByAccessToken(String accessToken);

}
