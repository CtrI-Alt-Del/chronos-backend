package br.com.chronos.core.collaboration.domain.events;

import java.time.LocalDate;
import java.util.List;

import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Date;

public class CollaboratorsPreparedForWorkEvent extends Event<CollaboratorsPreparedForWorkEvent.Payload> {
  public static final String NAME = "collaboration/collaborators.prepared.for.work";

  public static class Payload {
    public final List<String> collaboratorIds;
    public final List<Integer> collaboratorWorkloads;
    public final LocalDate date;

    public Payload(List<String> collaboratorIds, List<Integer> collaboratorWorkloads, LocalDate date) {
      this.collaboratorIds = collaboratorIds;
      this.collaboratorWorkloads = collaboratorWorkloads;
      this.date = date;
    }
  }

  public CollaboratorsPreparedForWorkEvent(Array<Collaborator> collaborators, Date date) {
    super(getPayload(collaborators, date));
  }

  private static Payload getPayload(Array<Collaborator> collaborators, Date date) {
    Array<String> collaboratorIds = Array.createAsEmpty();
    Array<Integer> collaboratorWorkloads = Array.createAsEmpty();

    for (var collaborator : collaborators.list()) {
      collaboratorIds.add(collaborator.getId().toString());
      collaboratorWorkloads.add(Integer.valueOf(collaborator.getWorkload().value()));
    }

    return new Payload(collaboratorIds.list(), collaboratorWorkloads.list(), date.value());
  }

}
