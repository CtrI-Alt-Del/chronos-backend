package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDateTime;
import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.exceptions.WorkdayLogNotFoundException;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class UpdateWorkdayHourBankUseCase {
  private final WorkdayLogsRepository repository;

  public UpdateWorkdayHourBankUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId, LocalDateTime dateTime, LocalTime time, boolean isCreditOperation) {
    var workdayLog = findWorkdayLog(
        Id.create(collaboratorId),
        Date.create(dateTime.toLocalDate()));
    workdayLog.updateHourBank(Time.create(time), Logical.create(isCreditOperation));
    repository.replace(workdayLog);
  }

  public WorkdayLog findWorkdayLog(Id collaboratorId, Date date) {
    var workdayLog = repository.findByCollaboratorAndDate(collaboratorId, date);
    if (workdayLog.isEmpty()) {
      throw new WorkdayLogNotFoundException();
    }
    return workdayLog.get();
  }
}
