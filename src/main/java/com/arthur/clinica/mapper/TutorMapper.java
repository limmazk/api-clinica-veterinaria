package com.arthur.clinica.mapper;

import com.arthur.clinica.dto.TutorRequestDTO;
import com.arthur.clinica.dto.TutorResponseDTO;
import com.arthur.clinica.model.Tutor;

public class TutorMapper {

    private TutorMapper() {
    }

    public static Tutor toEntity(TutorRequestDTO dto) {
        Tutor tutor = new Tutor();
        tutor.setNome(dto.nome());
        tutor.setEmail(dto.email());
        tutor.setTelefone(dto.telefone());
        return tutor;
    }

    public static void updateEntity(Tutor tutor, TutorRequestDTO dto) {
        tutor.setNome(dto.nome());
        tutor.setEmail(dto.email());
        tutor.setTelefone(dto.telefone());
    }

    public static TutorResponseDTO toResponseDTO(Tutor tutor) {
        return new TutorResponseDTO(tutor.getId(), tutor.getNome(), tutor.getEmail(), tutor.getTelefone());
    }
}
