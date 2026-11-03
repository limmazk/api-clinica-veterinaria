package com.arthur.clinica.controller;

import com.arthur.clinica.dto.ConsultaResponseDTO;
import com.arthur.clinica.dto.PetRequestDTO;
import com.arthur.clinica.dto.PetResponseDTO;
import com.arthur.clinica.service.ConsultaService;
import com.arthur.clinica.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;
    private final ConsultaService consultaService;

    public PetController(PetService petService, ConsultaService consultaService) {
        this.petService = petService;
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<PetResponseDTO> create(@RequestBody @Valid PetRequestDTO dto) {
        PetResponseDTO criado = petService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.id())
                .toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> getAll() {
        return ResponseEntity.ok(petService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.findById(id));
    }

    @GetMapping("/{id}/consultas")
    public ResponseEntity<List<ConsultaResponseDTO>> findConsultas(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.findByPet(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> update(@PathVariable Long id, @RequestBody @Valid PetRequestDTO dto) {
        return ResponseEntity.ok(petService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
