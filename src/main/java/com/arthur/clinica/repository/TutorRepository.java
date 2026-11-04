package com.arthur.clinica.repository;

import com.arthur.clinica.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    boolean existsByEmailIgnoreCase(String email);
}
