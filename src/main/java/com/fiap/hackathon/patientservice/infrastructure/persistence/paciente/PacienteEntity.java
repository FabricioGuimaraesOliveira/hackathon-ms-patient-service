package com.fiap.hackathon.patientservice.infrastructure.persistence.paciente;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.Objects;

@DynamoDbBean
public class PacienteEntity {

    private String cpf; // CPF será a chave de partição (ID)
    private String name;
    private String email;
    private String password;

    public PacienteEntity() {}

    public PacienteEntity(String cpf, String name, String email, String password) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("cpf")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDbAttribute("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDbAttribute("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PacienteEntity that)) return false;
        return Objects.equals(getCpf(), that.getCpf()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCpf(), getName(), getEmail(), getPassword());
    }

    @Override
    public String toString() {
        return "PacienteEntity [cpf=" + cpf + ", name=" + name + ", email=" + email + ", password=" + password + "]";
    }
}
