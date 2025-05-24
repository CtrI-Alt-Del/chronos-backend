package br.com.chronos.core.portal.domain.records;

import br.com.chronos.core.global.domain.entities.Responsible;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.portal.domain.dtos.CollaboratorWorkLeavesDto;

public record CollaboratorWorkLeaves(
    Array<WorkLeave> workLeaves,
    Responsible collaborator) {
  public static CollaboratorWorkLeaves create(CollaboratorWorkLeavesDto dto) {
    return new CollaboratorWorkLeaves(
        Array.create(dto.workLeaves.stream().map(WorkLeave::create).toList()),
        new Responsible(dto.collaborator));
  }

  public CollaboratorWorkLeavesDto toDto() {
    return new CollaboratorWorkLeavesDto()
        .setWorkLeaves(workLeaves.map(workleave -> workleave.toDto()).list())
        .setCollaborator(collaborator.getDto());
  }
}
