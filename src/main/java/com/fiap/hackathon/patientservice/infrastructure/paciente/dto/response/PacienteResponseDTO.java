package com.fiap.hackathon.patientservice.infrastructure.paciente.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PacienteResponseDTO {

    @Schema(description = "Nome do paciente", example = "João Silva")
    private String name;

    @Schema(description = "CPF do paciente", example = "123.456.789-10")
    private String cpf;

    @Schema(description = "Email do paciente", example = "joao.silva@email.com")
    private String email;
}
