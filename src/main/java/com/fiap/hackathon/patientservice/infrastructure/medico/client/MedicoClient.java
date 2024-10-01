package com.fiap.hackathon.patientservice.infrastructure.medico.client;

import com.fiap.hackathon.patientservice.infrastructure.medico.response.MedicoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "medico-service", url = "${medico-service.url}")
public interface MedicoClient {

    @GetMapping("/medicos")
    List<MedicoResponseDTO> listarMedicos();
}
