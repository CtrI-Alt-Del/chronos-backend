package br.com.chronos.server.queue.jobs.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.portal.domain.events.VacationSolicitationApprovedEvent;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.use_cases.ScheduleVacationUseCase;

@Component
public class ApproveVacationSolicitationJob {
    public static final String KEY = "portal/approve.vacation.solicitation.job";

    @Autowired
    private DayOffSchedulesRepository dayOffSchedulesRepository;

    public void handle(VacationSolicitationApprovedEvent.Payload payload) {
        System.out.println("OIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        var useCase = new ScheduleVacationUseCase(dayOffSchedulesRepository);
        java.time.LocalDate vacationDate = java.time.LocalDate.parse(payload.vacation());
        useCase.execute(vacationDate, payload.collaboratorId());
    }

}
