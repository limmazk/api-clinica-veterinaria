package com.arthur.clinica.dto;

import com.arthur.clinica.model.StatusConsulta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConsultaResponseDTO(
        Long id,
        Long petId,
        String petNome,
        LocalDateTime dataHora,
        String motivo,
        BigDecimal valor,
        StatusConsulta status
) {
}
