package com.fiap.hackathon.patientservice.usecases.paciente;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class PacienteAuthorizationUseCase {
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public PacienteAuthorizationUseCase() {
        this.algorithm = Algorithm.HMAC256("fiap");
        this.verifier = JWT.require(algorithm).build();
    }

    public String generateToken(String cpf) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("fiap");

            return JWT.create()
                    .withClaim("cpf", cpf)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean validarToken(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    public String obterCpfDoToken(String token) {
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("cpf").asString();
    }
}
