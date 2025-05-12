package br.com.chronos.core.auth.use_cases;

import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.records.Otp;
import br.com.chronos.core.auth.interfaces.AuthBroker;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.notification.interfaces.CacheProvider;

public class LoginUseCase {
  private final AuthenticationProvider authenticationProvider;
  private final AccountsRepository repository;
  private final CacheProvider cache;

  public LoginUseCase(
      AuthenticationProvider authenticationProvider,
      AccountsRepository repository,
      CacheProvider cache) {
    this.repository = repository;
    this.authenticationProvider = authenticationProvider;
    this.cache = cache;
  }

  public String execute(String otpCode) {
    var account = findAccount(otpCode);
    var accountDto = account.getDto();
    var jwt = authenticationProvider.login(accountDto, account.getPassword().value());
    return jwt;
  }

  private Account findAccount(String otpCode) {
    var otp = Otp.create(otpCode);
    var accountId = cache.get(otp.code().value());
    var account = repository.findById(Id.create(accountId));
    if (account.isEmpty()) {
      throw new NotFoundException("Conta não encontrada");
    }
    return account.get();
  }
}
