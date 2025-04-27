package br.com.chronos.core.collaboration.domain.events;

import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.global.domain.records.Password;

public class CollaboratorCreatedEvent extends Event<CollaboratorCreatedEvent.Payload> {
  public static final String NAME = "collaboration/collaborator.created";

  public static class Payload {
    public final String password;
    public final String email;
    public final String role;
    public final String collaborationSector;
    public final String collaboratorId;

    public Payload(
        String password,
        String email,
        String role,
        String collaborationSector,
        String collaboratorId) {
      this.password = password;
      this.email = email;
      this.role = role;
      this.collaborationSector = collaborationSector;
      this.collaboratorId = collaboratorId;
    }
  }

  public CollaboratorCreatedEvent(Collaborator collaborator, Password password) {
    super(new Payload(
        password.toString(),
        collaborator.getEmail().value(),
        collaborator.getRole().toString(),
        collaborator.getSector().toString(),
        collaborator.getId().toString()));
  }
}
