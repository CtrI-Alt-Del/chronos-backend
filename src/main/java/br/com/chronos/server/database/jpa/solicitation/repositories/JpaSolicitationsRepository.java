package br.com.chronos.server.database.jpa.solicitation.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.modules.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Role;
import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.domain.entities.WorkScheduleAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.server.database.jpa.solicitation.mappers.TimePunchLogAdjustmentSolicitationMapper;
import br.com.chronos.server.database.jpa.solicitation.mappers.WorkScheduleAdjustmentSolicitationMapper;
import br.com.chronos.server.database.jpa.solicitation.models.TimePunchLogAdjustmentSolicitationModel;
import br.com.chronos.server.database.jpa.solicitation.models.WorkScheduleAdjustmentSolicitationModel;

interface JpaTimePunchLogAdjustmentSolicitationModelsRepository
    extends JpaRepository<TimePunchLogAdjustmentSolicitationModel, UUID> {
  List<TimePunchLogAdjustmentSolicitationModel> findAllBySenderResponsibleAccountSector(Sector sector);

  List<TimePunchLogAdjustmentSolicitationModel> findAllBySenderResponsibleId(
      UUID userId);
}

interface JpaWorkScheduleAdjustmentSolicitationModelsRepository
    extends JpaRepository<WorkScheduleAdjustmentSolicitationModel, UUID> {
  List<WorkScheduleAdjustmentSolicitationModel> findAllBySenderResponsibleAccountSector(Sector sector);

  List<WorkScheduleAdjustmentSolicitationModel> findAllBySenderResponsibleId(
      UUID userId);
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
  @Transactional
  public void addTimePunchLogAdjustmentSolicitation(TimePunchLogAdjustmentSolicitation solicitation) {
    var timePunchAdjustmentSolicitationModel = timePunchAdjustmentMapper.toModel(solicitation);
    timePunchLogAdjustmentSolicitationModelsRepository.save(timePunchAdjustmentSolicitationModel);
  }

  @Override
  @Transactional
  public void addWorkScheduleAdjustmentSolicitation(WorkScheduleAdjustmentSolicitation solicitation) {
    var workScheduleAdjustmentSolicitationModel = workScheduleAdjustmentMapper.toModel(solicitation);
    workScheduleAdjustmentSolcitationModelsRepository.save(workScheduleAdjustmentSolicitationModel);
  }

  @Override
  @Transactional(readOnly = true)
  public Array<Solicitation> findMany(Sector sector, Role role, Id userId) {
    List<TimePunchLogAdjustmentSolicitationModel> timePunchSolicitations;
    List<WorkScheduleAdjustmentSolicitationModel> workScheduleSolicitations;

    if (role.isEmployee().isTrue()) {
      timePunchSolicitations = timePunchLogAdjustmentSolicitationModelsRepository
          .findAllBySenderResponsibleId(userId.value());

      workScheduleSolicitations = workScheduleAdjustmentSolcitationModelsRepository
          .findAllBySenderResponsibleId(userId.value());
    } else {
      
      timePunchSolicitations = timePunchLogAdjustmentSolicitationModelsRepository
          .findAllBySenderResponsibleAccountSector(sector);

      workScheduleSolicitations = workScheduleAdjustmentSolcitationModelsRepository
          .findAllBySenderResponsibleAccountSector(sector);
    }

    
    var timePunchSolicitationsEntities = timePunchSolicitations.stream()
        .map(timePunchAdjustmentMapper::toEntity)
        .toList();

    var workScheduleSolicitationsEntities = workScheduleSolicitations.stream()
        .map(workScheduleAdjustmentMapper::toEntity)
        .toList();

    
    var allSolicitations = new ArrayList<Solicitation>();
    allSolicitations.addAll(timePunchSolicitationsEntities);
    allSolicitations.addAll(workScheduleSolicitationsEntities);

    return new Array<>(allSolicitations);
  }

  @Override
  public void resolveSolicitation(Solicitation solicitation) {
    if (solicitation instanceof TimePunchLogAdjustmentSolicitation) {
      TimePunchLogAdjustmentSolicitation timePunchSolicitation = (TimePunchLogAdjustmentSolicitation) solicitation;

      timePunchSolicitation.status = solicitation.status;

      var timePunchSolicitationModel = timePunchAdjustmentMapper.toModel(timePunchSolicitation);
      timePunchLogAdjustmentSolicitationModelsRepository.save(timePunchSolicitationModel);

    } else if (solicitation instanceof WorkScheduleAdjustmentSolicitation) {
      WorkScheduleAdjustmentSolicitation workScheduleSolicitation = (WorkScheduleAdjustmentSolicitation) solicitation;

      workScheduleSolicitation.status = solicitation.status;

      var workScheduleSolicitationModel = workScheduleAdjustmentMapper.toModel(workScheduleSolicitation);
      workScheduleAdjustmentSolcitationModelsRepository.save(workScheduleSolicitationModel);
    }
  }

  @Override
  public Optional<Solicitation> findSolicitationById(Id solicitationId) {
    var timePunchSolicitationModel = timePunchLogAdjustmentSolicitationModelsRepository
        .findById(solicitationId.value());

    if (timePunchSolicitationModel.isPresent()) {
      return Optional.of(timePunchAdjustmentMapper.toEntity(timePunchSolicitationModel.get()));
    }

    var workScheduleSolicitationModel = workScheduleAdjustmentSolcitationModelsRepository
        .findById(solicitationId.value());

    if (workScheduleSolicitationModel.isPresent()) {
      return Optional.of(workScheduleAdjustmentMapper.toEntity(workScheduleSolicitationModel.get()));
    }
    return Optional.empty();

  }

}
