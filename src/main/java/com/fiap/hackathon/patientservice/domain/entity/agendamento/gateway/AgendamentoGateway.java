package com.fiap.hackathon.patientservice.domain.entity.agendamento.gateway;

import com.fiap.hackathon.patientservice.domain.entity.agendamento.model.Agendamento;

import java.util.List;


public interface AgendamentoGateway {

    List<Agendamento> buscarAgendamentosPorCpf(String cpf);

}
