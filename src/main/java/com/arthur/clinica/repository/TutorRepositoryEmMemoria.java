package com.arthur.clinica.repository;

import com.arthur.clinica.model.Tutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TutorRepositoryEmMemoria implements TutorRepository {
    private final Map<Long, Tutor> dados = new ConcurrentHashMap<>();

    private final AtomicLong sequencia = new AtomicLong(0);

    @Override
    public Tutor save(Tutor tutor) {
        if (tutor.getId() == null) {
            tutor.setId(sequencia.incrementAndGet());
        }
        dados.put(tutor.getId(), tutor);
        return tutor;
    }

    @Override
    public List<Tutor> findAll() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public Optional<Tutor> findById(Long id) {
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
    public boolean existsByEmail(String email) {
        return dados.values().stream()
                .anyMatch(tutor -> tutor.getEmail().equalsIgnoreCase(email));
    }
}
