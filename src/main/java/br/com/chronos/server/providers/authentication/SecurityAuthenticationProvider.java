package br.com.chronos.server.providers.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.chronos.core.modules.auth.domain.dtos.AccountDto;
import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.domain.exceptions.NotAuthenticatedException;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.modules.global.interfaces.providers.JwtProvider;
import br.com.chronos.server.infra.security.SecurityUser;
import br.com.chronos.core.modules.auth.domain.exceptions.DisabledAccountException;

public class SecurityAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtProvider jwtProvider;

  @Override
  public String login(AccountDto accountDto, String password) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(accountDto.email, password);
    try {
      authenticationManager.authenticate(authenticationToken);
      return jwtProvider.generateToken(accountDto);
    } catch (DisabledException e) {
      throw new DisabledAccountException();
    } catch (BadCredentialsException e) {
      throw new NotAuthenticatedException();
    }
  }

  @Override
  public AccountDto register(AccountDto dto) {
    var encryptedPassword = passwordEncoder.encode(dto.password);
    dto.setPassword(encryptedPassword);
    return dto;
  }

  @Override
  public void logout() {
    throw new UnsupportedOperationException("Unimplemented method 'logout'");
  }

  @Override
  public Account getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.getPrincipal() instanceof SecurityUser securityUser) {
      Account account = securityUser.getAccount();
      return account;
    }

    throw new NotAuthenticatedException();
  }
}
