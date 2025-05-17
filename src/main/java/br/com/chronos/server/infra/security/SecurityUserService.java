package br.com.chronos.server.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.exceptions.CredentialsNotValidException;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.auth.use_cases.GetAccountUseCase;

@Service
public class SecurityUserService implements UserDetailsService {

  @Autowired
  private AccountsRepository accountsRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    try {
      var useCase = new GetAccountUseCase(accountsRepository);
      var accountDto = useCase.execute(email);
      var account = new Account(accountDto);
      var securityUser = new SecurityUser(account);
      return securityUser;
    } catch (Exception e) {
      throw new CredentialsNotValidException();
    }
  }
}
