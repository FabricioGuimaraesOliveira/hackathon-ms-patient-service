package com.fiap.hackathon.patientservice.domain.entity.agendamento.model;

import com.fiap.hackathon.patientservice.domain.entity.agendamento.enums.StatusAgendamento;

import java.time.LocalDateTime;

public class Agendamento {

    private String cpfPaciente;
    private String medicoId;
    private LocalDateTime horarioAgendamento;
    private StatusAgendamento status; // Adiciona o status do agendamento

    // Construtor
    public Agendamento(String cpfPaciente, String medicoId, LocalDateTime horarioAgendamento, StatusAgendamento status) {
        this.cpfPaciente = cpfPaciente;
        this.medicoId = medicoId;
        this.horarioAgendamento = horarioAgendamento;
        this.status = status; // Inicializa o status
    }

    // Getters e Setters
    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public String getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(String medicoId) {
        this.medicoId = medicoId;
    }

    public LocalDateTime getHorarioAgendamento() {
        return horarioAgendamento;
    }

    public void setHorarioAgendamento(LocalDateTime horarioAgendamento) {
        this.horarioAgendamento = horarioAgendamento;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    // Método de validação
    public void validar() throws IllegalArgumentException {
        if (cpfPaciente == null || cpfPaciente.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF do paciente não pode ser nulo ou vazio.");
        }

        if (medicoId == null) {
            throw new IllegalArgumentException("ID do médico não pode ser nulo.");
        }

        if (horarioAgendamento == null) {
            throw new IllegalArgumentException("Horário de agendamento não pode ser nulo.");
        }
        if (horarioAgendamento.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("O horário de agendamento não pode estar no passado.");
        }

        if (status == null) {
            throw new IllegalArgumentException("O status do agendamento não pode ser nulo.");
        }
    }
}
