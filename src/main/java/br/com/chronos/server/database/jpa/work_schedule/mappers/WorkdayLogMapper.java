package br.com.chronos.server.database.jpa.work_schedule.mappers;

import org.springframework.stereotype.Component;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkdayLogModel;

@Component
public class WorkdayLogMapper {
	public WorkdayLogModel toModel(WorkdayLog entity) {
		var collaboratorModel = CollaboratorModel
				.builder()
				.id(entity.getResponsible().getId().value())
				.build();
		var timePunch = entity.getTimePunch();

		var model = WorkdayLogModel
				.builder()
				.id(entity.getId().value())
				.date(entity.getDate().value())
				.status(entity.getStatus().name())
				.firstClockIn(
						(timePunch.getFirstClockIn() != null)
								? timePunch.getFirstClockIn().value()
								: null)
				.firstClockOut(
						(timePunch.getFirstClockOut() != null)
								? timePunch.getFirstClockOut().value()
								: null)
				.secondClockIn(
						(timePunch.getSecondClockIn() != null)
								? timePunch.getSecondClockIn().value()
								: null)
				.secondClockOut(
						(timePunch.getSecondClockOut() != null)
								? timePunch.getSecondClockOut().value()
								: null)
				.hourBankCredit(entity.getHourBankCredit().value())
				.hourBankDebit(entity.getHourBankDebit().value())
				.workloadSchedule(entity.getWorkloadSchedule().value())
				.collaborator(collaboratorModel)
				.build();

		return model;
	}

	public WorkdayLog toEntity(WorkdayLogModel model) {
		var responsibleDto = new ResponsibleDto()
				.setId(model.getCollaborator().getId().toString())
				.setName(model.getCollaborator().getName())
				.setEmail(model.getCollaborator().getAccount().getEmail())
				.setCpf(model.getCollaborator().getCpf())
				.setSector(model.getCollaborator().getAccount().getSector().toString())
				.setRole(model.getCollaborator().getAccount().getRole().toString());

		var timePunchDto = new TimePunchDto()
				.setFirstClockIn(model.getFirstClockIn())
				.setFirstClockOut(model.getFirstClockOut())
				.setSecondClockIn(model.getSecondClockIn())
				.setSecondClockOut(model.getSecondClockOut());

		var dto = new WorkdayLogDto()
				.setId(model.getId().toString())
				.setDate(model.getDate())
				.setTimePunch(timePunchDto)
				.setStatus(model.getStatus().toString())
				.setWorkloadSchedule(model.getWorkloadSchedule())
				.setHourBankCredit(model.getHourBankCredit())
				.setHourBankDebit(model.getHourBankDebit())
				.setResponsible(new ResponsibleAggregateDto(responsibleDto));

		return new WorkdayLog(dto);
	}

}
