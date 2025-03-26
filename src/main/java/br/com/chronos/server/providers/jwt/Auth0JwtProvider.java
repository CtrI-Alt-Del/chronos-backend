package br.com.chronos.server.providers.jwt;

import java.time.ZoneOffset;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.chronos.core.modules.auth.domain.dtos.AccountDto;
import br.com.chronos.core.modules.auth.domain.exceptions.NotAuthenticatedException;
import br.com.chronos.core.modules.global.domain.records.DateTime;
import br.com.chronos.core.modules.global.interfaces.providers.EnvProvider;
import br.com.chronos.core.modules.global.interfaces.providers.JwtProvider;

public class Auth0JwtProvider implements JwtProvider {
  private final String secret;
  private final String issuer;
  private final Algorithm algorithm;

  private ObjectMapper objectMapper = new ObjectMapper();

  public Auth0JwtProvider(EnvProvider envProvider) {
    var jwt_secret = envProvider.get("JWT_SECRET");
    if (jwt_secret == null) {
      throw new IllegalStateException("Missing enviroment variable for jwt secret");
    }
    this.secret = jwt_secret;
    this.issuer = "chronos-server";
    this.algorithm = Algorithm.HMAC256(secret);
  }

  @Override
  public String generateToken(AccountDto accountDto) {
    var expirationDate = DateTime.createFromNow().addDays(7);
    String accountJson;
    try {
      accountJson = objectMapper.writeValueAsString(accountDto);
    } catch (JsonProcessingException e) {
      throw new NotAuthenticatedException();
    }
    var jwt = JWT.create()
        .withIssuer(issuer)
        .withSubject(accountJson)
        .withExpiresAt(expirationDate.value().toInstant(ZoneOffset.of("-03:00")))
        .sign(algorithm);
    return jwt;
  }

  @Override
  public String validateToken(String token) {
    try {
      var subject = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
      var subjectJson = subject.getSubject();
      Map<String, Object> subjectMap = objectMapper.readValue(subjectJson, new TypeReference<Map<String, Object>>() {});
      return (String) subjectMap.get("email");
    } catch (Exception exception) {
      throw new NotAuthenticatedException();
    }
  }

}
