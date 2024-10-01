package com.fiap.hackathon.patientservice.usecases.paciente;

import com.fiap.hackathon.patientservice.domain.entity.paciente.gateway.PacienteGateway;
import com.fiap.hackathon.patientservice.domain.entity.paciente.model.Paciente;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class PacienteUseCase {

    PacienteGateway pacienteGateway;
    PasswordEncoder passwordEncoder;

    public PacienteUseCase(PacienteGateway pacienteGateway,PasswordEncoder passwordEncoder) {
        this.pacienteGateway = pacienteGateway;
        this.passwordEncoder = passwordEncoder;

    }

    public Paciente cadastrarPaciente(String name, String cpf, String email, String password) {
        return pacienteGateway.cadastrarPaciente( name, cpf, email, password);
    }

    public Optional<Paciente> buscarPacientePorCpf(String cpf) {
        return pacienteGateway.buscarPacientePorCpf(cpf);
    }

    public Optional<Paciente> autenticarPaciente(String cpf, String password) {
        Optional<Paciente> pacienteOptional = pacienteGateway.buscarPacientePorCpf(cpf);

        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();
            if (passwordEncoder.matches(password, paciente.getPassword())) {
                return Optional.of(paciente);
            }
        }
        return Optional.empty();
    }
}


