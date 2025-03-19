package br.com.chronos.core.modules.global.interfaces.providers;

public interface JwtProvider {
  String generateToken(String subject);
  String validateToken(String token);
}
