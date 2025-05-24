package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.portal.domain.dtos.TimePunchAdjustmentSolicitationDto;
import br.com.chronos.core.portal.domain.entities.TimePunchAdjustmentSolicitation;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.work_schedule.use_cases.CreateSolicitationUseCase;

public class CreateTimePunchAdjustmentSolicitationUseCase extends CreateSolicitationUseCase {
  private final SolicitationsRepository repository;

  public CreateTimePunchAdjustmentSolicitationUseCase(
      SolicitationsRepository repository,
      PortalBroker broker) {
    super(broker);
    this.repository = repository;
  }

  public TimePunchAdjustmentSolicitationDto execute(
      TimePunchAdjustmentSolicitationDto dto,
      String senderResponsibleId,
      String collaboratorionSector) {
    var responsibleDto = new ResponsibleDto()
        .setId(senderResponsibleId)
        .setSector(collaboratorionSector);
    var senderResponsibleDto = new ResponsibleAggregateDto(responsibleDto);
    dto.setSenderResponsible(senderResponsibleDto);

    var solicitation = new TimePunchAdjustmentSolicitation(dto);
    repository.add(solicitation);

    sendSolicitationCreatedEvent(solicitation);
    return solicitation.getDto();
  }
}
