package com.arthur.clinica.repository;

import com.arthur.clinica.model.Pet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PetRepositoryEmMemoria implements PetRepository {
    private final Map<Long, Pet> dados = new ConcurrentHashMap<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    @Override
    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet.setId(sequencia.incrementAndGet());
        }
        dados.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public List<Pet> findAll() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public boolean existsById(Long id) {
        return dados.containsKey(id);
    }

    @Override
    public void deleteById(Long id) {
        dados.remove(id);
    }

    @Override
    public List<Pet> findByTutorId(Long tutorId) {
        return dados.values().stream()
                .filter(pet -> pet.getTutor().getId().equals(tutorId))
                .toList();
    }

    @Override
    public boolean existsByTutorId(Long tutorId) {
        return dados.values().stream()
                .anyMatch(pet -> pet.getTutor().getId().equals(tutorId));
    }
}
