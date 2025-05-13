package br.com.chronos.core.auth.domain.events;

import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.records.Otp;
import br.com.chronos.core.global.domain.abstracts.Event;

public class AuthenticationRequestedEvent extends Event<AuthenticationRequestedEvent.Payload> {
  public static final String NAME = "auth/authentication.requested";

  public static record Payload(String accountEmail, String otpCode) {
  }

  public AuthenticationRequestedEvent(Account account, Otp otp) {
    super(new Payload(
        account.getEmail().text().value(),
        otp.code().value()));
  }
}
