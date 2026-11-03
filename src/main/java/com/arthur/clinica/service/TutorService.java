package com.arthur.clinica.service;

import com.arthur.clinica.dto.TutorRequestDTO;
import com.arthur.clinica.dto.TutorResponseDTO;
import com.arthur.clinica.exception.BusinessException;
import com.arthur.clinica.exception.ResourceNotFoundException;
import com.arthur.clinica.mapper.TutorMapper;
import com.arthur.clinica.model.Tutor;
import com.arthur.clinica.repository.PetRepository;
import com.arthur.clinica.repository.TutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {
    private final TutorRepository tutorRepository;
    private final PetRepository petRepository;

    public TutorService(TutorRepository tutorRepository, PetRepository petRepository) {
        this.tutorRepository = tutorRepository;
        this.petRepository = petRepository;
    }

    public TutorResponseDTO create(TutorRequestDTO dto) {
        if (tutorRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Já existe um tutor com o e-mail " + dto.email());
        }
        Tutor salvo = tutorRepository.save(TutorMapper.toEntity(dto));
        return TutorMapper.toResponseDTO(salvo);
    }

    public List<TutorResponseDTO> getAll() {
        return tutorRepository.findAll().stream()
                .map(TutorMapper::toResponseDTO)
                .toList();
    }

    public TutorResponseDTO findById(Long id) {
        return TutorMapper.toResponseDTO(buscarTutor(id));
    }

    public TutorResponseDTO update(Long id, TutorRequestDTO dto) {
        Tutor tutor = buscarTutor(id);

        boolean emailMudou = !tutor.getEmail().equalsIgnoreCase(dto.email());
        if (emailMudou && tutorRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Já existe um tutor com o e-mail " + dto.email());
        }

        TutorMapper.updateEntity(tutor, dto);
        return TutorMapper.toResponseDTO(tutorRepository.save(tutor));
    }

    public void delete(Long id) {
        if (!tutorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tutor não encontrado com id " + id);
        }
        if (petRepository.existsByTutorId(id)) {
            throw new BusinessException("Não é possível excluir um tutor que possui pets cadastrados");
        }
        tutorRepository.deleteById(id);
    }

    public Tutor buscarTutor(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor não encontrado com id " + id));
    }
}
