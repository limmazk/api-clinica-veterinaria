package com.arthur.clinica.mapper;

import com.arthur.clinica.dto.PetRequestDTO;
import com.arthur.clinica.dto.PetResponseDTO;
import com.arthur.clinica.model.Pet;
import com.arthur.clinica.model.Tutor;

public class PetMapper {
    private PetMapper() {
    }

    public static Pet toEntity(PetRequestDTO dto, Tutor tutor) {
        Pet pet = new Pet();
        updateEntity(pet, dto, tutor);
        return pet;
    }

    public static void updateEntity(Pet pet, PetRequestDTO dto, Tutor tutor) {
        pet.setNome(dto.nome());
        pet.setEspecie(dto.especie());
        pet.setRaca(dto.raca());
        pet.setDataNascimento(dto.dataNascimento());
        pet.setTutor(tutor);
    }

    public static PetResponseDTO toResponseDTO(Pet pet) {
        return new PetResponseDTO(
                pet.getId(),
                pet.getNome(),
                pet.getEspecie(),
                pet.getRaca(),
                pet.getDataNascimento(),
                pet.getTutor().getId(),
                pet.getTutor().getNome()
        );
    }
}
