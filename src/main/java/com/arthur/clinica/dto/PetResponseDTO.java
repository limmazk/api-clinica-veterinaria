package com.arthur.clinica.dto;

import com.arthur.clinica.model.Especie;

import java.time.LocalDate;

public record PetResponseDTO(
        Long id,
        String nome,
        Especie especie,
        String raca,
        LocalDate dataNascimento,
        Long tutorId,
        String tutorNome
) {
}
