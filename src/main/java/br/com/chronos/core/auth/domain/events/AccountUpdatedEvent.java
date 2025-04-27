package br.com.chronos.core.auth.domain.events;

import br.com.chronos.core.global.domain.abstracts.Event;

public class AccountUpdatedEvent extends Event<AccountUpdatedEvent.Payload> {
  public static final String NAME = "auth/account.updated";

  public static class Payload {
    public final String collaboratorId;
    public final String collaboratorName;
    public final String collaboratorCpf;
    public final byte collaboratorWorkload;

    public Payload(
        String collaboratorId,
        String collaboratorName,
        String collaboratorCpf,
        byte collaboratorWorkload) {
      this.collaboratorId = collaboratorId;
      this.collaboratorName = collaboratorName;
      this.collaboratorCpf = collaboratorCpf;
      this.collaboratorWorkload = collaboratorWorkload;
    }
  }

  public AccountUpdatedEvent(
      String collaboratorId,
      String collaboratorName,
      String collaboratorCpf,
      byte collaboratorWorkload) {
    super(new Payload(
        collaboratorId,
        collaboratorName,
        collaboratorCpf,
        collaboratorWorkload));
  }

}
