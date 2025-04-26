package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.dtos.DayOffScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.solicitation.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffScheduleAdjustmentRepository;
import br.com.chronos.core.work_schedule.domain.entities.DayOffSchedule;

public class CreateDayOffScheduleAdjustmentSolicitationUseCase {

  private final DayOffScheduleAdjustmentRepository solicitationsRepository;

  public CreateDayOffScheduleAdjustmentSolicitationUseCase(
      DayOffScheduleAdjustmentRepository solicitationsRepository) {
    this.solicitationsRepository = solicitationsRepository;
  }

  public DayOffScheduleAdjustmentSolicitationDto execute(DayOffScheduleAdjustmentSolicitationDto dto,
      Id collaboratorId) {
    DayOffSchedule dayOffSchedule = new DayOffSchedule(dto.dayOffSchedule);
    var senderResponsibleDto = new ResponsibleDto().setId(collaboratorId.value().toString());
    dto.setSenderResponsible(new ResponsibleAggregateDto(senderResponsibleDto));
    dto.setDayOffSchedule(dayOffSchedule.getDto());
    var solicitation = new DayOffScheduleAdjustmentSolicitation(dto);
    solicitationsRepository.add(solicitation);
    return solicitation.getDto();
  }
}
