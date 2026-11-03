package com.arthur.clinica.repository;

import com.arthur.clinica.model.Consulta;

import java.util.List;
import java.util.Optional;

public interface ConsultaRepository {
    Consulta save(Consulta consulta);

    List<Consulta> findAll();

    Optional<Consulta> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Consulta> findByPetId(Long petId);

    boolean existsByPetId(Long petId);
}
