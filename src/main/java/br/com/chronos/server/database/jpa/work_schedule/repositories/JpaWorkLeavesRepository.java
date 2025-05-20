package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.records.CollaboratorWorkLeave;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkLeavesRepository;
import br.com.chronos.server.database.jpa.work_schedule.daos.WorkLeaveDao;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WorkLeaveMapper;

public class JpaWorkLeavesRepository implements WorkLeavesRepository {
  @Autowired
  private WorkLeaveDao workLeaveDao;

  @Autowired
  private WorkLeaveMapper workLeaveMapper;

  @Override
  public void add(CollaboratorWorkLeave workLeave, Id collaboratorId) {
    var workLeaveModel = workLeaveMapper.toModel(workLeave);
    workLeaveDao.save(workLeaveModel);
  }

  @Override
  public Optional<CollaboratorWorkLeave> findByCollaboratorAndDate(Id collaboratorId, Date date) {
    var workLeaveModel = workLeaveDao.findByCollaboratorAndDate(
        collaboratorId.value(),
        date.value());
    return workLeaveModel.map(workLeaveMapper::toEntity);
  }

}
