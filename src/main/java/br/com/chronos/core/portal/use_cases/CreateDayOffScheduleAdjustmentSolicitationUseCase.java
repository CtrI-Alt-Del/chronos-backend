package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.portal.domain.dtos.DayOffScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.portal.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.work_schedule.use_cases.CreateSolicitationUseCase;

public class CreateDayOffScheduleAdjustmentSolicitationUseCase extends CreateSolicitationUseCase {
  private final SolicitationsRepository solicitationsRepository;

  public CreateDayOffScheduleAdjustmentSolicitationUseCase(
      SolicitationsRepository solicitationsRepository,
      PortalBroker broker) {
    super(broker);
    this.solicitationsRepository = solicitationsRepository;
  }

  public DayOffScheduleAdjustmentSolicitationDto execute(
      DayOffScheduleAdjustmentSolicitationDto dto,
      String senderResponsibleId) {
    var senderResponsibleDto = new ResponsibleAggregateDto().setId(senderResponsibleId);
    dto.setSenderResponsible(senderResponsibleDto);
    var solicitation = new DayOffScheduleAdjustmentSolicitation(dto);
    solicitationsRepository.add(solicitation);
    sendSolicitationCreatedEvent(solicitation);
    return solicitation.getDto();
  }
}
