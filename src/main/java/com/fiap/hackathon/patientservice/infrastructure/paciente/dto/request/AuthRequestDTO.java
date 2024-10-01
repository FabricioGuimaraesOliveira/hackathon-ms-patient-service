package com.fiap.hackathon.patientservice.infrastructure.paciente.dto.request;

import jakarta.validation.constraints.NotEmpty;

public class AuthRequestDTO {

    @NotEmpty(message = "CPF não pode ser vazio")
    private String cpf;

    @NotEmpty(message = "Senha não pode ser vazia")
    private String password;

    // Construtor padrão (necessário para a desserialização)
    public AuthRequestDTO() {
    }

    // Construtor com parâmetros
    public AuthRequestDTO(String cpf, String password) {
        this.cpf = cpf;
        this.password = password;
    }

    // Getters e Setters

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
