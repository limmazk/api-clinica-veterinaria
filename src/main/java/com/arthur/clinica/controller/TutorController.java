package com.arthur.clinica.controller;

import com.arthur.clinica.dto.PetResponseDTO;
import com.arthur.clinica.dto.TutorRequestDTO;
import com.arthur.clinica.dto.TutorResponseDTO;
import com.arthur.clinica.service.PetService;
import com.arthur.clinica.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tutores")
public class TutorController {
    private final TutorService tutorService;
    private final PetService petService;

    public TutorController(TutorService tutorService, PetService petService) {
        this.tutorService = tutorService;
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<TutorResponseDTO> create(@RequestBody @Valid TutorRequestDTO dto) {
        TutorResponseDTO criado = tutorService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.id())
                .toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<TutorResponseDTO>> getAll() {
        return ResponseEntity.ok(tutorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.findById(id));
    }

    @GetMapping("/{id}/pets")
    public ResponseEntity<List<PetResponseDTO>> findPets(@PathVariable Long id) {
        return ResponseEntity.ok(petService.findByTutor(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorResponseDTO> update(@PathVariable Long id, @RequestBody @Valid TutorRequestDTO dto) {
        return ResponseEntity.ok(tutorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tutorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
