package com.arthur.clinica.service;

import com.arthur.clinica.dto.TutorRequestDTO;
import com.arthur.clinica.dto.TutorResponseDTO;
import com.arthur.clinica.exception.BusinessException;
import com.arthur.clinica.exception.ResourceNotFoundException;
import com.arthur.clinica.mapper.TutorMapper;
import com.arthur.clinica.model.Tutor;
import com.arthur.clinica.repository.PetRepository;
import com.arthur.clinica.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final TutorRepository tutorRepository;
    private final PetRepository petRepository;

    @Transactional
    public TutorResponseDTO create(TutorRequestDTO dto) {
        if (tutorRepository.existsByEmailIgnoreCase(dto.email())) {
            throw new BusinessException("Já existe um tutor com o e-mail " + dto.email());
        }
        Tutor salvo = tutorRepository.save(TutorMapper.toEntity(dto));
        return TutorMapper.toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<TutorResponseDTO> getAll() {
        return tutorRepository.findAll().stream()
                .map(TutorMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TutorResponseDTO findById(Long id) {
        return TutorMapper.toResponseDTO(buscarTutor(id));
    }

    @Transactional
    public TutorResponseDTO update(Long id, TutorRequestDTO dto) {
        Tutor tutor = buscarTutor(id);

        boolean emailMudou = !tutor.getEmail().equalsIgnoreCase(dto.email());
        if (emailMudou && tutorRepository.existsByEmailIgnoreCase(dto.email())) {
            throw new BusinessException("Já existe um tutor com o e-mail " + dto.email());
        }

        TutorMapper.updateEntity(tutor, dto);
        return TutorMapper.toResponseDTO(tutorRepository.save(tutor));
    }

    @Transactional
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
