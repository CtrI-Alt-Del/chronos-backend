package br.com.chronos.server.api.controllers.portal.solicitations;

import java.util.List;

import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Month;
import br.com.chronos.core.portal.domain.dtos.CollaboratorWorkLeavesDto;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveDto;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class GetWorkLeaveCalendarUseCase {
  private final SolicitationsRepository repository;

  public GetWorkLeaveCalendarUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public List<CollaboratorWorkLeavesDto> execute(
      List<ResponsibleDto> senders,
      int year,
      int month) {
    Array<CollaboratorWorkLeavesDto> collaboratorWorkLeaves = Array.createAsEmpty();

    for (var sender : senders) {
      var solicitations = repository.findAllApprovedWorkLeaveSolicitationsBySenderAndMonth(
          Id.create(sender.id),
          Month.create(year, month));

      var workLeaves = solicitations.map(solicitation -> {
        var justification = solicitation.getJustification() != null
            ? solicitation.getJustification().getDto()
            : null;

        return new WorkLeaveDto()
            .setStartedAt(solicitation.getStartedAt().value())
            .setEndedAt(solicitation.getEndedAt().value())
            .setDescription(solicitation.getDescription().value())
            .setIsVacation(solicitation.getIsVacation().value())
            .setJustification(justification);
      });
      workLeaves.sort((workLeave) -> workLeave.startedAt);

      var collaboratorWorkLeavesDto = new CollaboratorWorkLeavesDto()
          .setWorkLeaves(workLeaves.list())
          .setCollaborator(sender);

      collaboratorWorkLeaves.add(collaboratorWorkLeavesDto);
    }

    return collaboratorWorkLeaves.list();
  }
}
