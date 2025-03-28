package br.com.chronos.core.modules.solicitation.use_cases;

import br.com.chronos.core.modules.solicitation.domain.dtos.WorkScheduleAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.entities.WorkScheduleAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class CreateWorkScheduleAdjustmentSolicitationUseCase {

  private final SolicitationsRepository solicitationsRepository;
  private final WorkSchedulesRepository workSchedulesRepository;

  public CreateWorkScheduleAdjustmentSolicitationUseCase(SolicitationsRepository solicitationsRepository,
      WorkSchedulesRepository workSchedulesRepository) {
    this.workSchedulesRepository = workSchedulesRepository;
    this.solicitationsRepository = solicitationsRepository;
  }

  public WorkScheduleAdjustmentSolicitationDto execute(WorkScheduleAdjustmentSolicitationDto dto) {
    var workSchedule = new WorkSchedule(dto.workScheduleDto);
    workSchedulesRepository.add(workSchedule);
    dto.setWorkScheduleDto(workSchedule.getDto());
    var solicitation = new WorkScheduleAdjustmentSolicitation(dto);
    solicitationsRepository.addWorkScheduleAdjustmentSolicitation(solicitation);
    return solicitation.getDto();
  }
}
