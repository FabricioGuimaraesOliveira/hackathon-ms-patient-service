package com.fiap.hackathon.patientservice.usecases.medico;


import com.fiap.hackathon.patientservice.infrastructure.medico.client.MedicoClient;
import com.fiap.hackathon.patientservice.infrastructure.medico.response.MedicoResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoUseCase {

    private final MedicoClient medicoClient;

    public MedicoUseCase(MedicoClient medicoClient) {
        this.medicoClient = medicoClient;
    }

    public List<MedicoResponseDTO> listarMedicosComHorarios() {
        return medicoClient.listarMedicos();
    }
}
