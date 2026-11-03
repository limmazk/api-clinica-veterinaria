package com.arthur.clinica.service;

import com.arthur.clinica.dto.ConsultaRequestDTO;
import com.arthur.clinica.dto.ConsultaResponseDTO;
import com.arthur.clinica.exception.BusinessException;
import com.arthur.clinica.exception.ResourceNotFoundException;
import com.arthur.clinica.model.Consulta;
import com.arthur.clinica.model.Especie;
import com.arthur.clinica.model.Pet;
import com.arthur.clinica.model.StatusConsulta;
import com.arthur.clinica.model.Tutor;
import com.arthur.clinica.repository.ConsultaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {
    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private PetService petService;

    @InjectMocks
    private ConsultaService consultaService;

    private Pet pet;
    private ConsultaRequestDTO request;

    @BeforeEach
    void setUp() {
        Tutor tutor = new Tutor(1L, "Ana Souza", "ana@email.com", "11999998888");

        pet = new Pet();
        pet.setId(1L);
        pet.setNome("Thor");
        pet.setEspecie(Especie.CACHORRO);
        pet.setDataNascimento(LocalDate.of(2020, 3, 15));
        pet.setTutor(tutor);

        request = new ConsultaRequestDTO(1L, LocalDateTime.now().plusDays(3), "Vacina anual", new BigDecimal("150.00"));
    }

    @Test
    void create_deveSalvarConsultaAgendada_quandoPetExiste() {
        when(petService.buscarPet(1L)).thenReturn(pet);
        when(consultaRepository.save(any(Consulta.class))).thenAnswer(inv -> {
            Consulta c = inv.getArgument(0);
            c.setId(10L);
            return c;
        });

        ConsultaResponseDTO response = consultaService.create(request);

        assertEquals(10L, response.id());
        assertEquals("Thor", response.petNome());
        assertEquals(StatusConsulta.AGENDADA, response.status());
        verify(consultaRepository).save(any(Consulta.class));
    }

    @Test
    void create_deveLancarNotFound_quandoPetNaoExiste() {
        when(petService.buscarPet(1L)).thenThrow(new ResourceNotFoundException("Pet não encontrado com id 1"));

        assertThrows(ResourceNotFoundException.class, () -> consultaService.create(request));
        verify(consultaRepository, never()).save(any());
    }

    @Test
    void realizar_deveMudarStatusParaRealizada_quandoAgendada() {
        Consulta consulta = consultaComStatus(StatusConsulta.AGENDADA);
        when(consultaRepository.findById(5L)).thenReturn(Optional.of(consulta));
        when(consultaRepository.save(consulta)).thenReturn(consulta);

        ConsultaResponseDTO response = consultaService.realizar(5L);

        assertEquals(StatusConsulta.REALIZADA, response.status());
    }

    @Test
    void cancelar_deveLancarBusinessException_quandoJaRealizada() {
        when(consultaRepository.findById(5L)).thenReturn(Optional.of(consultaComStatus(StatusConsulta.REALIZADA)));

        assertThrows(BusinessException.class, () -> consultaService.cancelar(5L));
        verify(consultaRepository, never()).save(any());
    }

    @Test
    void update_deveLancarBusinessException_quandoCancelada() {
        when(consultaRepository.findById(5L)).thenReturn(Optional.of(consultaComStatus(StatusConsulta.CANCELADA)));

        assertThrows(BusinessException.class, () -> consultaService.update(5L, request));
        verify(petService, never()).buscarPet(any());
        verify(consultaRepository, never()).save(any());
    }

    @Test
    void findById_deveLancarNotFound_quandoNaoExiste() {
        when(consultaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> consultaService.findById(99L));
    }

    private Consulta consultaComStatus(StatusConsulta status) {
        Consulta consulta = new Consulta();
        consulta.setId(5L);
        consulta.setPet(pet);
        consulta.setDataHora(LocalDateTime.now().plusDays(1));
        consulta.setMotivo("Check-up");
        consulta.setValor(new BigDecimal("120.00"));
        consulta.setStatus(status);
        return consulta;
    }
}
