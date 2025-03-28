package br.com.chronos.server.database.jpa.solicitation.repositories;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.domain.entities.WorkScheduleAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.server.database.jpa.solicitation.mappers.TimePunchLogAdjustmentSolicitationMapper;
import br.com.chronos.server.database.jpa.solicitation.mappers.WorkScheduleAdjustmentSolicitationMapper;
import br.com.chronos.server.database.jpa.solicitation.models.TimePunchLogAdjustmentSolicitationModel;
import br.com.chronos.server.database.jpa.solicitation.models.WorkScheduleAdjustmentSolicitationModel;

interface JpaTimePunchLogAdjustmentSolicitationModelsRepository
    extends JpaRepository<TimePunchLogAdjustmentSolicitationModel, UUID> {
}

interface JpaWorkScheduleAdjustmentSolicitationModelsRepository
    extends JpaRepository<WorkScheduleAdjustmentSolicitationModel, UUID> {
}

public class JpaSolicitationsRepository implements SolicitationsRepository {

  @Autowired
  JpaTimePunchLogAdjustmentSolicitationModelsRepository timePunchLogAdjustmentSolicitationModelsRepository;

  @Autowired
  JpaWorkScheduleAdjustmentSolicitationModelsRepository workScheduleAdjustmentSolcitationModelsRepository;

  @Autowired
  TimePunchLogAdjustmentSolicitationMapper timePunchAdjustmentMapper;

  @Autowired
  WorkScheduleAdjustmentSolicitationMapper workScheduleAdjustmentMapper;

  @Override
  public void addTimePunchLogAdjustmentSolicitation(TimePunchLogAdjustmentSolicitation solicitation) {
    var timePunchAdjustmentSolicitationModel = timePunchAdjustmentMapper.toModel(solicitation);
    timePunchLogAdjustmentSolicitationModelsRepository.save(timePunchAdjustmentSolicitationModel);
  }

  @Override
  public void addWorkScheduleAdjustmentSolicitation(WorkScheduleAdjustmentSolicitation solicitation) {
    var workScheduleAdjustmentSolicitationModel = workScheduleAdjustmentMapper.toModel(solicitation);
    workScheduleAdjustmentSolcitationModelsRepository.save(workScheduleAdjustmentSolicitationModel);
  }

}
