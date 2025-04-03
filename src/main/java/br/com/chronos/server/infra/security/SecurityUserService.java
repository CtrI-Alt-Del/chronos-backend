package br.com.chronos.server.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.domain.exceptions.NotAuthenticatedException;
import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.auth.use_cases.GetAccountUseCase;

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
      throw new NotAuthenticatedException();
    }
  }
}
