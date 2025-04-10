package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.dtos.DayOffScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.solicitation.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffScheduleAdjustmentRepository;
import br.com.chronos.core.work_schedule.domain.entities.DayOffSchedule;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;

public class CreateDayOffScheduleAdjustmentSolicitationUseCase {

  private final DayOffScheduleAdjustmentRepository solicitationsRepository;
  private final DayOffSchedulesRepository dayOffSchedulesRepository;

  public CreateDayOffScheduleAdjustmentSolicitationUseCase(
      DayOffScheduleAdjustmentRepository solicitationsRepository,
      DayOffSchedulesRepository dayOffSchedulesRepository) {
    this.dayOffSchedulesRepository = dayOffSchedulesRepository;
    this.solicitationsRepository = solicitationsRepository;
  }

  public DayOffScheduleAdjustmentSolicitationDto execute(DayOffScheduleAdjustmentSolicitationDto dto,
      Id collaboratorId) {
    DayOffSchedule dayOffSchedule = new DayOffSchedule(dto.dayOffScheduleDto);
    dayOffSchedulesRepository.add(dayOffSchedule, collaboratorId);
    var senderResponsibleDto = new ResponsibleDto()
        .setId(collaboratorId.value().toString());
    dto.setSenderResponsible(new ResponsibleAggregateDto(senderResponsibleDto));
    dto.setDayOffSchedule(dayOffSchedule.getDto());
    var solicitation = new DayOffScheduleAdjustmentSolicitation(dto);
    solicitationsRepository.add(solicitation);
    return solicitation.getDto();
  }
}
