package com.fiap.hackathon.patientservice.infrastructure.messaging;//package com.fiap.greentracefood.infrastructure.messaging;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoRequestDTO {

    @Schema(description = "ID do médico", example = "123e4567-e89b-12d3-a456-426614174000")
    private String medicoId;

    @Schema(description = "CPF do paciente", example = "123.456.789-10")
    private String cpfPaciente;

    @Schema(description = "Data do agendamento", example = "2024-09-30")
    private String dataAgenda;

    @Schema(description = "Hora do agendamento", example = "09:45")
    private String horaAgenda;

    @Schema(description = "Status do agendamento", example = "AGENDADO")
    private String status;
}
