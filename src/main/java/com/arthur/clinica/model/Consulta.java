package com.arthur.clinica.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Consulta {
    private Long id;
    private Pet pet;
    private LocalDateTime dataHora;
    private String motivo;
    private BigDecimal valor;
    private StatusConsulta status = StatusConsulta.AGENDADA;

    public Consulta() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public StatusConsulta getStatus() { return status; }
    public void setStatus(StatusConsulta status) { this.status = status; }
}
