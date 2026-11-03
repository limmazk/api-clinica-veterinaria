package com.arthur.clinica.service;

import com.arthur.clinica.dto.ConsultaRequestDTO;
import com.arthur.clinica.dto.ConsultaResponseDTO;
import com.arthur.clinica.exception.BusinessException;
import com.arthur.clinica.exception.ResourceNotFoundException;
import com.arthur.clinica.mapper.ConsultaMapper;
import com.arthur.clinica.model.Consulta;
import com.arthur.clinica.model.Pet;
import com.arthur.clinica.model.StatusConsulta;
import com.arthur.clinica.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {
    private final ConsultaRepository consultaRepository;
    private final PetService petService;

    public ConsultaService(ConsultaRepository consultaRepository, PetService petService) {
        this.consultaRepository = consultaRepository;
        this.petService = petService;
    }

    public ConsultaResponseDTO create(ConsultaRequestDTO dto) {
        Pet pet = petService.buscarPet(dto.petId());
        Consulta salva = consultaRepository.save(ConsultaMapper.toEntity(dto, pet));
        return ConsultaMapper.toResponseDTO(salva);
    }

    public List<ConsultaResponseDTO> getAll() {
        return consultaRepository.findAll().stream()
                .map(ConsultaMapper::toResponseDTO)
                .toList();
    }

    public ConsultaResponseDTO findById(Long id) {
        return ConsultaMapper.toResponseDTO(buscarConsulta(id));
    }

    public List<ConsultaResponseDTO> findByPet(Long petId) {
        petService.buscarPet(petId);
        return consultaRepository.findByPetId(petId).stream()
                .map(ConsultaMapper::toResponseDTO)
                .toList();
    }

    public ConsultaResponseDTO update(Long id, ConsultaRequestDTO dto) {
        Consulta consulta = buscarConsulta(id);
        exigirAgendada(consulta, "alterar");
        Pet pet = petService.buscarPet(dto.petId());
        ConsultaMapper.updateEntity(consulta, dto, pet);
        return ConsultaMapper.toResponseDTO(consultaRepository.save(consulta));
    }

    public ConsultaResponseDTO realizar(Long id) {
        Consulta consulta = buscarConsulta(id);
        exigirAgendada(consulta, "realizar");
        consulta.setStatus(StatusConsulta.REALIZADA);
        return ConsultaMapper.toResponseDTO(consultaRepository.save(consulta));
    }

    public ConsultaResponseDTO cancelar(Long id) {
        Consulta consulta = buscarConsulta(id);
        exigirAgendada(consulta, "cancelar");
        consulta.setStatus(StatusConsulta.CANCELADA);
        return ConsultaMapper.toResponseDTO(consultaRepository.save(consulta));
    }

    private void exigirAgendada(Consulta consulta, String acao) {
        if (consulta.getStatus() != StatusConsulta.AGENDADA) {
            throw new BusinessException("Não é possível " + acao + " uma consulta com status " + consulta.getStatus());
        }
    }

    private Consulta buscarConsulta(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada com id " + id));
    }
}
