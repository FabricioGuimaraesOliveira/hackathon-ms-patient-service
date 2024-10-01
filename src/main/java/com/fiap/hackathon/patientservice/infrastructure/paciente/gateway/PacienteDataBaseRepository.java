package com.fiap.hackathon.patientservice.infrastructure.paciente.gateway;

import com.fiap.hackathon.patientservice.domain.entity.paciente.gateway.PacienteGateway;
import com.fiap.hackathon.patientservice.domain.entity.paciente.model.Paciente;
import com.fiap.hackathon.patientservice.infrastructure.persistence.paciente.PacienteEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Optional;

public class PacienteDataBaseRepository implements PacienteGateway {

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<PacienteEntity> table;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public PacienteDataBaseRepository(DynamoDbEnhancedClient enhancedClient, String tableName) {
        this.enhancedClient = enhancedClient;
        this.table = enhancedClient.table(tableName, TableSchema.fromBean(PacienteEntity.class));
    }

    @Override
    public Paciente cadastrarPaciente(String name, String cpf, String email, String password) {
        String passwordEncoded = passwordEncoder.encode(password);
        PacienteEntity pacienteEntity = new PacienteEntity(cpf, name, email, passwordEncoded);
        table.putItem(pacienteEntity);

        return new Paciente(
                pacienteEntity.getCpf(),
                pacienteEntity.getName(),
                pacienteEntity.getEmail(),
                pacienteEntity.getPassword()
        );
    }

    @Override
    public Optional<Paciente> buscarPacientePorCpf(String cpf) {
        Key key = Key.builder()
                .partitionValue(cpf)
                .build();

        PacienteEntity pacienteEntity = table.getItem(r -> r.key(key));

        if (pacienteEntity != null) {
            Paciente paciente = new Paciente(
                    pacienteEntity.getCpf(),
                    pacienteEntity.getName(),
                    pacienteEntity.getEmail(),
                    pacienteEntity.getPassword()
            );
            return Optional.of(paciente);
        }

        return Optional.empty();
    }
}
