package com.arthur.clinica.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConsultaRequestDTO(

        @NotNull(message = "O petId é obrigatório")
        Long petId,

        @NotNull(message = "A data e hora são obrigatórias")
        @Future(message = "A consulta deve ser agendada para uma data futura")
        LocalDateTime dataHora,

        @NotBlank(message = "O motivo é obrigatório")
        String motivo,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valor
) {
}
