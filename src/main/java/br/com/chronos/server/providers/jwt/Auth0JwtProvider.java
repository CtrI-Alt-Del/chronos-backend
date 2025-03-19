package br.com.chronos.server.providers.jwt;

import java.time.ZoneOffset;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.chronos.core.modules.auth.domain.exceptions.NotAuthenticatedException;
import br.com.chronos.core.modules.global.domain.records.DateTime;
import br.com.chronos.core.modules.global.interfaces.providers.EnvProvider;
import br.com.chronos.core.modules.global.interfaces.providers.JwtProvider;

public class Auth0JwtProvider implements JwtProvider {
  private final String secret;
  private final String issuer;
  private final Algorithm algorithm;

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
  public String generateToken(String email) {
    var expirationDate = DateTime.createFromNow().addDays(7);
    var jwt = JWT.create()
        .withIssuer(issuer)
        .withSubject(email)
        .withExpiresAt(expirationDate.value().toInstant(ZoneOffset.of("-03:00")))
        .sign(algorithm);
    return jwt;
  }

  @Override
  public String validateToken(String token) {
    try{
      var subject = JWT.require(algorithm).withIssuer(issuer).build().verify(token).getSubject();
      return subject;
    }catch(JWTVerificationException exception){
      throw new NotAuthenticatedException();
    }
  }

}
