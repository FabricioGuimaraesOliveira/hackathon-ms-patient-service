package com.fiap.hackathon.patientservice.infrastructure.agendamento.controller;

import com.fiap.hackathon.patientservice.infrastructure.medico.response.MedicoResponseDTO;
import com.fiap.hackathon.patientservice.infrastructure.messaging.AgendamentoRequestDTO;
import com.fiap.hackathon.patientservice.usecases.agendamento.AgendamentoUseCase;
import com.fiap.hackathon.patientservice.usecases.medico.MedicoUseCase;
import com.fiap.hackathon.patientservice.usecases.paciente.PacienteAuthorizationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@Tag(name = "agendamento", description = "API responsável pelo gerenciamento de agendamentos.")
public class AgendamentoController {

    private final AgendamentoUseCase agendamentoUseCase;
    private final MedicoUseCase medicoUseCase;
    private final PacienteAuthorizationUseCase pacienteAuthorizationUseCase;

    public AgendamentoController(AgendamentoUseCase agendamentoUseCase, MedicoUseCase medicoUseCase, PacienteAuthorizationUseCase pacienteAuthorizationUseCase) {
        this.agendamentoUseCase = agendamentoUseCase;
        this.medicoUseCase = medicoUseCase;
        this.pacienteAuthorizationUseCase = pacienteAuthorizationUseCase;
    }

//    @Operation(summary = "Listar todos os médicos disponíveis")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Médicos listados com sucesso",
//                    content = { @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = MedicoResponseDTO.class)) }),
//            @ApiResponse(responseCode = "404", description = "Nenhum médico encontrado",
//                    content = @Content) })
//    @GetMapping("/medicos")
//    public ResponseEntity<List<MedicoResponseDTO>> listarMedicosDisponiveis() {
//        List<MedicoResponseDTO> medicos = medicoUseCase.listarMedicosComHorarios();
//        if (medicos.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(medicos);
//    }

//    @Operation(summary = "Realizar um agendamento de consulta com um médico",
//            description = "Este endpoint permite que um paciente autenticado agende uma consulta com um médico. O médico é identificado pelo seu ID, e o horário da consulta deve ser fornecido no formato ISO.",
//            parameters = {
//                    @Parameter(name = "Authorization", description = "Token de autorização JWT no cabeçalho", example = "Bearer <seu_token>")
//            })
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Consulta agendada com sucesso",
//                    content = {@Content(mediaType = "application/json")}),
//            @ApiResponse(responseCode = "400", description = "Erro ao agendar a consulta",
//                    content = @Content),
//            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente",
//                    content = @Content)
//    })
    @PostMapping("/agendarConsulta")
    public ResponseEntity<String> agendarConsulta(@RequestHeader("Authorization") String authorizationHeader,
                                                  @Valid @RequestBody AgendamentoRequestDTO agendamentoRequestDTO) {
        String token = authorizationHeader.replace("Bearer ", "");

        if (!pacienteAuthorizationUseCase.validarToken(token)) {
            return ResponseEntity.status(401).body("Token inválido ou ausente.");
        }

        String cpf = pacienteAuthorizationUseCase.obterCpfDoToken(token);

        // Agendar a consulta
        agendamentoUseCase.agendarConsulta(agendamentoRequestDTO);
        return ResponseEntity.ok("Agendamento enviado com sucesso. Aguarde a resposta !!.");
    }

//    @Operation(summary = "Consultar agendamentos pelo CPF do paciente",
//            description = "Este endpoint permite que um paciente consulte todos os seus agendamentos usando o CPF.",
//            parameters = {
//                    @Parameter(name = "cpf", description = "CPF do paciente", example = "123.456.789-00")
//            })
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Agendamentos encontrados com sucesso",
//                    content = { @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = Agendamento.class)) }),
//            @ApiResponse(responseCode = "404", description = "Nenhum agendamento encontrado para este CPF",
//                    content = @Content)
//    })
//    @GetMapping("/consultarPorCpf")
//    public ResponseEntity<List<Agendamento>> consultarAgendamentosPorCpf(@RequestParam String cpf) {
//        List<Agendamento> agendamentos = agendamentoUseCase.consultarAgendamentosPorCpf(cpf);
//        if (agendamentos.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(agendamentos);
//    }
}
