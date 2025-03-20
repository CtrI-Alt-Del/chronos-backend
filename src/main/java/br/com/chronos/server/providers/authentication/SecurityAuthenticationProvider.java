package br.com.chronos.server.providers.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.modules.global.interfaces.providers.JwtProvider;

public class SecurityAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtProvider jwtProvider;

  @Override
  public String login(String email, String password) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
    authenticationManager.authenticate(authenticationToken);
    var jwt = jwtProvider.generateToken(email);
    return jwt;
  }

  @Override
  public CollaboratorDto register(CollaboratorDto dto) {
    var encryptedPassword = passwordEncoder.encode(dto.password);
    dto.setPassword(encryptedPassword);
    return dto;
  }

  @Override
  public void logout() {
    throw new UnsupportedOperationException("Unimplemented method 'logout'");
  }
}
