package br.com.fiap.comunicaplus_api_main.service;

import br.com.fiap.comunicaplus_api_main.model.Token;
import br.com.fiap.comunicaplus_api_main.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    private static final Long DURATION = 10L; // duração do token em minutos
    private static final Algorithm ALG = Algorithm.HMAC256("secret"); // chave secreta para assinar o token

    public Token createToken(User user) {
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(DURATION);
        Date expirationDate = Date.from(expirationTime.toInstant(ZoneOffset.ofHours(-3)));

        String token = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withExpiresAt(expirationDate)
                .sign(ALG);

        // Criar o Token com 3 parâmetros: token, expiration em milissegundos e tipo "Bearer"
        return new Token(token, expirationDate.getTime(), "Bearer");
    }

    public User getUserFromToken(String token) {
        var decoded = JWT.require(ALG)
                .build()
                .verify(token);

        return User.builder()
                .id(Long.parseLong(decoded.getSubject()))
                .email(decoded.getClaim("email").asString())
                .build();
    }
}
