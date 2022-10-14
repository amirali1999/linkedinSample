package com.example.linkedinSample.repository;

import com.example.linkedinSample.model.Skill;
import com.example.linkedinSample.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByName(String name);

    Optional<Skill> findByNameAndSubject(String name, Subject subject);

    @Query(value = "select s from Skill s where s.name like :name")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Page<Skill> findContainName(@Param(value = "name") String name, Pageable pageable);

    Page<Skill> findByVerify(boolean verify, Pageable pageable);
}
