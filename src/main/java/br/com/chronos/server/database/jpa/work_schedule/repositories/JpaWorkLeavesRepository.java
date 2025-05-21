package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.records.WorkLeave;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkLeavesRepository;
import br.com.chronos.server.database.jpa.work_schedule.daos.WorkLeaveDao;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WorkLeaveMapper;

public class JpaWorkLeavesRepository implements WorkLeavesRepository {
  @Autowired
  private WorkLeaveDao dao;

  @Autowired
  private WorkLeaveMapper mapper;

  @Override
  public void add(WorkLeave workLeave, Id collaboratorId) {
    var workLeaveModel = mapper.toModel(workLeave);
    dao.save(workLeaveModel);
  }

  @Override
  public Optional<WorkLeave> findByCollaboratorAndDate(Id collaboratorId, Date date) {
    var workLeaveModels = dao.findByCollaboratorAndDate(
        collaboratorId.value(),
        date.value());
    return workLeaveModels.map(mapper::toEntity);
  }
}
