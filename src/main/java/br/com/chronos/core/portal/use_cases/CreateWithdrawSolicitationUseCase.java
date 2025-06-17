
package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveSolicitationDto;
import br.com.chronos.core.portal.domain.entities.WorkLeaveSolicitation;
import br.com.chronos.core.portal.domain.events.WorkLeaveSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.exceptions.WorkLeaveSolicitationDateRangeException;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.work_schedule.use_cases.CreateSolicitationUseCase;

public class CreateWithdrawSolicitationUseCase extends CreateSolicitationUseCase {
  private final SolicitationsRepository repository;

  public CreateWithdrawSolicitationUseCase(SolicitationsRepository repository, PortalBroker broker) {
    super(broker);
    this.repository = repository;
  }

  public WorkLeaveSolicitationDto execute(
      WorkLeaveSolicitationDto dto,
      String senderResponsibleId,
      String collaboratorionSector) {
    findByDateRange(
        Id.create(senderResponsibleId),
        DateRange.create(dto.startedAt, dto.endedAt, 1));

    var responsibleDto = new ResponsibleDto()
        .setId(senderResponsibleId)
        .setSector(collaboratorionSector);
    var senderResponsibleDto = new ResponsibleAggregateDto(responsibleDto);
    dto.setSenderResponsible(senderResponsibleDto);

    var solicitation = new WorkLeaveSolicitation(dto);
    repository.add(solicitation);

    sendSolicitationCreatedEvent(solicitation);
    return solicitation.getDto();
  }

  private void findByDateRange(Id collaboratorId, DateRange dateRange) {
    var solicitations = repository.findAllWorkLeaveSolicitationByCollaboratorAndDateRange(collaboratorId, dateRange);
    if (solicitations.isEmpty().isFalse()) {
      throw new WorkLeaveSolicitationDateRangeException(false);
    }
  }
}
