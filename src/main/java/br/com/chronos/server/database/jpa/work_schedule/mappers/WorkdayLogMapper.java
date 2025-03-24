package br.com.chronos.server.database.jpa.work_schedule.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkdayLogModel;

@Service
public class WorkdayLogMapper {
    @Autowired
    private TimePunchMapper TimePunchMapper;

    public WorkdayLogModel toModel(WorkdayLog entity) {
        var collaboratorModel = CollaboratorModel
                .builder()
                .id(entity.getResponsible().getId().value())
                .build();

        var model = WorkdayLogModel.builder()
                .id(entity.getId().value())
                .date(entity.getDate().value())
                .status(entity.getStatus().name())
                .collaborator(collaboratorModel)
                .build();

        return model;
    }

    public WorkdayLog toEntity(WorkdayLogModel model) {
        var responsibleDto = new ResponsibleDto()
                .setEmail(model.getCollaborator().getAccount().getEmail())
                .setName(model.getCollaborator().getName())
                .setRole(model.getCollaborator().getAccount().getRole().toString())
                .setSector(model.getCollaborator().getSector().toString());

        var dto = new WorkdayLogDto()
                .setId(model.getId().toString())
                .setDate(model.getDate())
                .setResponsible(model.getCollaborator().getId().toString(), responsibleDto)
                .setTimePunchSchedule(TimePunchMapper.toDto(model.getTimePunchSchedule()))
                .setTimePunchLog(TimePunchMapper.toDto(model.getTimePunchLog()))
                .setStatus(model.getStatus().toString());

        return new WorkdayLog(dto);
    }

}
