package com.arthur.clinica.repository;

import com.arthur.clinica.model.Consulta;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ConsultaRepositoryEmMemoria implements ConsultaRepository {
    private final Map<Long, Consulta> dados = new ConcurrentHashMap<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    @Override
    public Consulta save(Consulta consulta) {
        if (consulta.getId() == null) {
            consulta.setId(sequencia.incrementAndGet());
        }
        dados.put(consulta.getId(), consulta);
        return consulta;
    }

    @Override
    public List<Consulta> findAll() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public Optional<Consulta> findById(Long id) {
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
    public List<Consulta> findByPetId(Long petId) {
        return dados.values().stream()
                .filter(consulta -> consulta.getPet().getId().equals(petId))
                .toList();
    }

    @Override
    public boolean existsByPetId(Long petId) {
        return dados.values().stream()
                .anyMatch(consulta -> consulta.getPet().getId().equals(petId));
    }
}
