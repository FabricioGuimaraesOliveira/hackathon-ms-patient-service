package com.fiap.hackathon.patientservice.infrastructure.configuration;

import com.fiap.hackathon.patientservice.domain.entity.paciente.gateway.PacienteGateway;
import com.fiap.hackathon.patientservice.infrastructure.messaging.AgendamentoSender;
import com.fiap.hackathon.patientservice.infrastructure.paciente.gateway.PacienteDataBaseRepository;
import com.fiap.hackathon.patientservice.usecases.agendamento.AgendamentoUseCase;
import com.fiap.hackathon.patientservice.usecases.paciente.PacienteAuthorizationUseCase;
import com.fiap.hackathon.patientservice.usecases.paciente.PacienteUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

@Configuration
public class BeanConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	PacienteGateway createPacienteGateway(DynamoDbEnhancedClient enhancedClient, @Value("${dynamodb.tablename}") String tableName) {
		return new PacienteDataBaseRepository(enhancedClient,tableName);
	}

	@Bean
	PacienteUseCase createPacienteUseCase(PacienteGateway pacienteGateway,PasswordEncoder passwordEncoder) {
		return new PacienteUseCase(pacienteGateway,passwordEncoder);
	}
	@Bean
	AgendamentoUseCase createAgendamentoUseCase(AgendamentoSender agendamentoSender) {
		return new AgendamentoUseCase(agendamentoSender);
	}
	@Bean
	PacienteAuthorizationUseCase createPacienteAuthorizationUseCase() {
		return new PacienteAuthorizationUseCase();
	}

}
