package br.com.chronos.server.database.jpa.collaborator.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WorkScheduleMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkScheduleModel;

@Component
public class CollaboratorMapper {
  @Autowired
  private WorkScheduleMapper workScheduleMapper;
  public CollaboratorModel toModel(Collaborator entity) {
    var workScheduleModel = WorkScheduleModel.builder().id(entity.getWorkScheduleId().value()).build();
    var model = CollaboratorModel.builder()
        .id(entity.getId().value())
        .name(entity.getName().value())
        .cpf(entity.getCpf().value())
        .workSchedule(workScheduleModel)
        .build();

    return model;
  }

  public Collaborator toEntity(CollaboratorModel model) {
    var dto = new CollaboratorDto()
        .setId(model.getId().toString())
        .setName(model.getName().toString())
        .setEmail(model.getAccount().getEmail().toString())
        .setCpf(model.getCpf().toString())
        .setSector(model.getAccount().getSector().toString())
        .setRole(model.getAccount().getRole().toString())
        .setActive(model.getAccount().getIsActive())
        .setWorkScheduleId(model.getWorkSchedule().getId().toString());

    return new Collaborator(dto);
  }
}
