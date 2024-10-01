package com.fiap.hackathon.patientservice.usecases.agendamento;


import com.fiap.hackathon.patientservice.domain.entity.agendamento.enums.StatusAgendamento;
import com.fiap.hackathon.patientservice.domain.entity.agendamento.model.Agendamento;
import com.fiap.hackathon.patientservice.infrastructure.messaging.AgendamentoRequestDTO;
import com.fiap.hackathon.patientservice.infrastructure.messaging.AgendamentoSender;

import java.time.LocalDateTime;

public class AgendamentoUseCase {
    AgendamentoSender agendamentoSender;

    public AgendamentoUseCase(AgendamentoSender agendamentoSender) {
        this.agendamentoSender = agendamentoSender;

    }

    public void agendarConsulta(AgendamentoRequestDTO agendamentoRequestDTO) {

        agendamentoSender.sendAgendamentoMessage(agendamentoRequestDTO);
    }


}
