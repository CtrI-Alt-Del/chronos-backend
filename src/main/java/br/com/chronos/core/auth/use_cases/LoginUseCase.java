package br.com.chronos.core.auth.use_cases;

import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.exceptions.CredentialsNotValidException;
import br.com.chronos.core.auth.domain.records.Otp;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.interfaces.providers.JwtProvider;
import br.com.chronos.core.notification.interfaces.CacheProvider;

public class LoginUseCase {
  private final AccountsRepository repository;
  private final CacheProvider cacheProvider;
  private final JwtProvider jwtProvider;

  public LoginUseCase(
      AccountsRepository repository,
      CacheProvider cacheProvider,
      JwtProvider jwtProvider) {
    this.repository = repository;
    this.cacheProvider = cacheProvider;
    this.jwtProvider = jwtProvider;
  }

  public String execute(String otpCode) {
    var account = findAccount(otpCode);
    var accountDto = account.getDto();
    var jwt = jwtProvider.generateToken(accountDto);
    return jwt;
  }

  private Account findAccount(String otpCode) {
    var otp = Otp.create(otpCode);
    var accountId = cacheProvider.get(otp.code().value());

    if (accountId == null) {
      throw new CredentialsNotValidException();
    }

    var account = repository.findById(Id.create(accountId));
    if (account.isEmpty()) {
      throw new NotFoundException("Conta não encontrada");
    }
    cacheProvider.delete(otpCode);
    return account.get();
  }
}
