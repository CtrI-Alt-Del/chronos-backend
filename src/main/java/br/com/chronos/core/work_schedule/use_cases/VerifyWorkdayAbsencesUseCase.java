package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class VerifyWorkdayAbsencesUseCase {
  private final WorkdayLogsRepository repository;

  public VerifyWorkdayAbsencesUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public void execute(LocalDate date) {
    var workdayLogs = repository.findAllByDate(Date.create(date));

    workdayLogs = workdayLogs.filter(workdayLog -> {
      var isAbsence = workdayLog.verifyAbsense();
      return isAbsence.isTrue();
    });
    repository.replaceMany(workdayLogs);
  }
}
