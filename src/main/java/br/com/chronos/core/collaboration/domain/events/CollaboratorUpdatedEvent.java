package br.com.chronos.core.collaboration.domain.events;

import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.global.domain.records.Id;

public class CollaboratorUpdatedEvent extends Event<CollaboratorUpdatedEvent.Payload> {
  public static final String KEY = "collaboration/collaborator.updated";

  public static class Payload {
    public final String email;
    public final String role;
    public final String sector;
    public final String responsibleCollaboratorId;
    public final String collaboratorId;

    public Payload(String email, String role, String sector, String collaboratorId, String responsibleCollaboratorId) {
      this.email = email;
      this.role = role;
      this.sector = sector;
      this.responsibleCollaboratorId = responsibleCollaboratorId;
      this.collaboratorId = collaboratorId;
    }
  }

  public CollaboratorUpdatedEvent(Collaborator collaborator, Id responsibleCollaboratorId) {
    super(new Payload(
        collaborator.getEmail().value(),
        collaborator.getRole().value().toString(),
        collaborator.getSector().value().toString(),
        collaborator.getId().value().toString(),
        responsibleCollaboratorId.value().toString()));
  }

}
