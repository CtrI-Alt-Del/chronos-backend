package br.com.chronos.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.chronos.core.modules.auth.domain.exceptions.NotAuthenticatedException;
import br.com.chronos.core.modules.auth.use_cases.GetAccountUseCase;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;

@Service
public class SecurityUserService implements UserDetailsService {

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    try {
      var useCase = new GetAccountUseCase(collaboratorsRepository);
      var collaboratorDto = useCase.execute(email);
      var collaborator = new Collaborator(collaboratorDto);
      var securityUser = new SecurityUser(collaborator);
      return securityUser;
    } catch (Exception e) {
      throw new NotAuthenticatedException(e.getMessage());
    }
  }
}
