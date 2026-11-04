package com.arthur.clinica.controller;

import com.arthur.clinica.dto.ConsultaRequestDTO;
import com.arthur.clinica.dto.ConsultaResponseDTO;
import com.arthur.clinica.service.ConsultaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> create(@RequestBody @Valid ConsultaRequestDTO dto) {
        ConsultaResponseDTO criada = consultaService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criada.id())
                .toUri();
        return ResponseEntity.created(location).body(criada);
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> getAll() {
        return ResponseEntity.ok(consultaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ConsultaRequestDTO dto) {
        return ResponseEntity.ok(consultaService.update(id, dto));
    }

    @PatchMapping("/{id}/realizar")
    public ResponseEntity<ConsultaResponseDTO> realizar(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.realizar(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ConsultaResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.cancelar(id));
    }
}
