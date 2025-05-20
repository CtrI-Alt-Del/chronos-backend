package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.events.VacationScheduleNotFoundException;
import br.com.chronos.core.work_schedule.domain.entities.DayOffSchedule;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;

public class ScheduleVacationUseCase {
    private final DayOffSchedulesRepository repository;

    public ScheduleVacationUseCase(DayOffSchedulesRepository repository) {
        this.repository = repository;
    }

    public void execute(LocalDate startDate, LocalDate endDate, String collaboratorIdValue) {
        var collaboratorId = Id.create(collaboratorIdValue);
        var vacationSchedule = findVacationSchedule(collaboratorId);
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            vacationSchedule.schedule(Date.create(currentDate));
            currentDate = currentDate.plusDays(1);
        }
        repository.replace(vacationSchedule, collaboratorId);
    }
    
    public DayOffSchedule findVacationSchedule(Id collaboratorId) {
        var vacationSchedule = repository.findByCollaborator(collaboratorId);
        if (vacationSchedule.isEmpty()) {
           throw new VacationScheduleNotFoundException();
        }
        return vacationSchedule.get();
    }
}
