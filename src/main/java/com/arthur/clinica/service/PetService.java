package com.arthur.clinica.service;

import com.arthur.clinica.dto.PetRequestDTO;
import com.arthur.clinica.dto.PetResponseDTO;
import com.arthur.clinica.exception.BusinessException;
import com.arthur.clinica.exception.ResourceNotFoundException;
import com.arthur.clinica.mapper.PetMapper;
import com.arthur.clinica.model.Pet;
import com.arthur.clinica.model.Tutor;
import com.arthur.clinica.repository.ConsultaRepository;
import com.arthur.clinica.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final ConsultaRepository consultaRepository;
    private final TutorService tutorService;

    @Transactional
    public PetResponseDTO create(PetRequestDTO dto) {
        Tutor tutor = tutorService.buscarTutor(dto.tutorId());
        Pet salvo = petRepository.save(PetMapper.toEntity(dto, tutor));
        return PetMapper.toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<PetResponseDTO> getAll() {
        return petRepository.findAll().stream()
                .map(PetMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PetResponseDTO findById(Long id) {
        return PetMapper.toResponseDTO(buscarPet(id));
    }

    @Transactional(readOnly = true)
    public List<PetResponseDTO> findByTutor(Long tutorId) {
        tutorService.buscarTutor(tutorId);
        return petRepository.findByTutorId(tutorId).stream()
                .map(PetMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public PetResponseDTO update(Long id, PetRequestDTO dto) {
        Pet pet = buscarPet(id);
        Tutor tutor = tutorService.buscarTutor(dto.tutorId());
        PetMapper.updateEntity(pet, dto, tutor);
        return PetMapper.toResponseDTO(petRepository.save(pet));
    }

    @Transactional
    public void delete(Long id) {
        if (!petRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pet não encontrado com id " + id);
        }
        if (consultaRepository.existsByPetId(id)) {
            throw new BusinessException("Não é possível excluir um pet que possui consultas registradas");
        }
        petRepository.deleteById(id);
    }

    public Pet buscarPet(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com id " + id));
    }
}
