package com.fiap.hackathon.patientservice.infrastructure.paciente.controller;

import com.fiap.hackathon.patientservice.domain.entity.paciente.model.Paciente;
import com.fiap.hackathon.patientservice.infrastructure.paciente.dto.request.AuthRequestDTO;
import com.fiap.hackathon.patientservice.infrastructure.paciente.dto.response.PacienteResponseDTO;
import com.fiap.hackathon.patientservice.usecases.paciente.PacienteAuthorizationUseCase;
import com.fiap.hackathon.patientservice.usecases.paciente.PacienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
@Tag(name = "paciente", description = "API responsável pelo gerenciamento de pacientes.")
public class PacienteController {

    private final PacienteUseCase pacienteUseCase;
    private final PacienteAuthorizationUseCase pacienteAuthorizationUseCase;
    private final ModelMapper modelMapper;

    public PacienteController(PacienteUseCase pacienteUseCase, PacienteAuthorizationUseCase pacienteAuthorizationUseCase, ModelMapper modelMapper) {
        this.pacienteUseCase = pacienteUseCase;
        this.pacienteAuthorizationUseCase = pacienteAuthorizationUseCase;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Cadastrar um novo paciente",
            description = "Este endpoint permite cadastrar um novo paciente no sistema.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "PacienteExemplo",
                                    summary = "Exemplo de corpo para cadastro de paciente",
                                    description = "Um exemplo de como cadastrar um paciente com CPF, email e senha",
                                    value = "{ \"name\": \"João da Silva\", \"cpf\": \"123.456.789-00\", \"email\": \"joao.silva@email.com\", \"password\": \"senha123\" }"
                            )
                    )
            ))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente cadastrado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o paciente",
                    content = @Content)})
    @PostMapping("/cadastrar")
    public ResponseEntity<PacienteResponseDTO> cadastrarPaciente(@RequestBody Paciente paciente) {
        Paciente novoPaciente = pacienteUseCase.cadastrarPaciente(
                paciente.getName(),
                paciente.getCpf(),
                paciente.getEmail(),
                paciente.getPassword()
        );

        PacienteResponseDTO responseDTO = modelMapper.map(novoPaciente, PacienteResponseDTO.class);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Buscar paciente por CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                    content = @Content)})
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<PacienteResponseDTO> buscarPacientePorCpf(@PathVariable String cpf) {
        Optional<Paciente> paciente = pacienteUseCase.buscarPacientePorCpf(cpf);

        return paciente.map(p -> ResponseEntity.ok(modelMapper.map(p, PacienteResponseDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Autenticar paciente e gerar token de acesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                    content = @Content)})
    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> autenticarPaciente(@RequestBody AuthRequestDTO request) {
        Optional<Paciente> paciente = pacienteUseCase.autenticarPaciente(request.getCpf(), request.getPassword());
        if (paciente.isPresent()) {
            String token = pacienteAuthorizationUseCase.generateToken(paciente.get().getCpf());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Credenciais inválidas"));
        }
    }
}