package com.example.linkedinSample.repository;


import com.example.linkedinSample.entity.RefreshToken;
import com.example.linkedinSample.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query(value = "DELETE from RefreshToken u where u.users.id =:id")
    void deleteAccessTokens(@Param("id")long id);
//
//    @Modifying
//    int deleteByUser(Users users);
}