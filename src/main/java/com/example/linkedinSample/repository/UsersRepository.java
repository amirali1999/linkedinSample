package com.example.linkedinSample.repository;

import com.example.linkedinSample.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);

    @Modifying
    @Query(value = "update Users u set u.password = :password where u.id = :id")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void updatePassword(@Param(value = "id") long id, @Param(value = "password") String password);
}
