package br.com.chronos.core.portal.domain.dtos;

import java.util.ArrayList;
import java.util.List;

import br.com.chronos.core.global.domain.dtos.ResponsibleDto;

public class CollaboratorWorkLeavesDto {
  public ResponsibleDto collaborator;
  public List<WorkLeaveDto> workLeaves = new ArrayList<>();

  public CollaboratorWorkLeavesDto setWorkLeaves(List<WorkLeaveDto> workLeaves) {
    this.workLeaves = workLeaves;
    return this;
  }

  public CollaboratorWorkLeavesDto setCollaborator(ResponsibleDto collaborator) {
    this.collaborator = collaborator;
    return this;
  }
}
