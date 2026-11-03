package com.arthur.clinica.repository;

import com.arthur.clinica.model.Tutor;

import java.util.List;
import java.util.Optional;

public interface TutorRepository {
    Tutor save(Tutor tutor);

    List<Tutor> findAll();

    Optional<Tutor> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    boolean existsByEmail(String email);
}
