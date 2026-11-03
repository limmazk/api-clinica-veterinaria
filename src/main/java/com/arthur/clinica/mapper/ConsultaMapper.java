package com.arthur.clinica.mapper;

import com.arthur.clinica.dto.ConsultaRequestDTO;
import com.arthur.clinica.dto.ConsultaResponseDTO;
import com.arthur.clinica.model.Consulta;
import com.arthur.clinica.model.Pet;

public class ConsultaMapper {
    private ConsultaMapper() {
    }

    public static Consulta toEntity(ConsultaRequestDTO dto, Pet pet) {
        Consulta consulta = new Consulta();
        updateEntity(consulta, dto, pet);
        return consulta;
    }

    public static void updateEntity(Consulta consulta, ConsultaRequestDTO dto, Pet pet) {
        consulta.setPet(pet);
        consulta.setDataHora(dto.dataHora());
        consulta.setMotivo(dto.motivo());
        consulta.setValor(dto.valor());
    }

    public static ConsultaResponseDTO toResponseDTO(Consulta consulta) {
        return new ConsultaResponseDTO(
                consulta.getId(),
                consulta.getPet().getId(),
                consulta.getPet().getNome(),
                consulta.getDataHora(),
                consulta.getMotivo(),
                consulta.getValor(),
                consulta.getStatus()
        );
    }
}
