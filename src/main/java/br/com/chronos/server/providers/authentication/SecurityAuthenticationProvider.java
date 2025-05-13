package br.com.chronos.server.providers.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.chronos.core.auth.domain.dtos.AccountDto;
import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.exceptions.DisabledAccountException;
import br.com.chronos.core.auth.domain.exceptions.CredentialsNotValidException;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.server.infra.security.SecurityUser;

@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public Logical validateCredentials(String email, String password) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
    try {
      Authentication authentication = authenticationManager.authenticate(authenticationToken);
      return Logical.create(authentication.isAuthenticated());
    } catch (DisabledException e) {
      throw new DisabledAccountException();
    } catch (BadCredentialsException e) {
      return Logical.createAsFalse();
    } catch (Exception e) {
      return Logical.createAsFalse();
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
  public Account getAccount() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.getPrincipal() instanceof SecurityUser securityUser) {
      Account account = securityUser.getAccount();
      return account;
    }

    throw new CredentialsNotValidException();
  }
}
