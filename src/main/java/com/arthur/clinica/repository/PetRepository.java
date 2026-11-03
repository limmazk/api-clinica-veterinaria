package com.arthur.clinica.repository;

import com.arthur.clinica.model.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {
    Pet save(Pet pet);

    List<Pet> findAll();

    Optional<Pet> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Pet> findByTutorId(Long tutorId);

    boolean existsByTutorId(Long tutorId);
}
