package com.example.linkedinSample.repository;

import com.example.linkedinSample.model.EType;
import com.example.linkedinSample.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<List<Subject>> findByTypeAndCompanyAndLanguageAndVersion(EType type, String company, String language, double version);
}
