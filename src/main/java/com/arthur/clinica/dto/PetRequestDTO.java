package com.arthur.clinica.dto;

import com.arthur.clinica.model.Especie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record PetRequestDTO(

        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotNull(message = "A espécie é obrigatória")
        Especie especie,

        String raca,

        @NotNull(message = "A data de nascimento é obrigatória")
        @PastOrPresent(message = "A data de nascimento não pode estar no futuro")
        LocalDate dataNascimento,

        @NotNull(message = "O tutorId é obrigatório")
        Long tutorId
) {
}
