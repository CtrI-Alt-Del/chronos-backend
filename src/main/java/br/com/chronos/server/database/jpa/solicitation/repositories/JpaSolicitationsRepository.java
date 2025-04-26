package br.com.chronos.server.database.jpa.solicitation.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.solicitation.domain.entities.DayOffSolicitation;
import br.com.chronos.core.solicitation.domain.entities.ExcuseAbsenceSolicitation;
import br.com.chronos.core.solicitation.domain.entities.PaidOvertimeSolicitation;
import br.com.chronos.core.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.solicitation.domain.records.SolicitationType;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffScheduleAdjustmentRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffSolicitationRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.TimePunchLogAdjustmentRepository;
import br.com.chronos.server.database.jpa.solicitation.daos.ExcuseAbsenceSolicitationDao;
import br.com.chronos.server.database.jpa.solicitation.daos.PaidOvertimeSolicitationDao;
import br.com.chronos.server.database.jpa.solicitation.mappers.ExcuseAbsenceSolicitationMapper;
import br.com.chronos.server.database.jpa.solicitation.mappers.PaidOvertimeSolicitationMapper;
import br.com.chronos.server.database.jpa.solicitation.mappers.SolicitationMapper;
import br.com.chronos.server.database.jpa.solicitation.models.SolicitationModel;

interface JpaSolicitationsModelRepository extends JpaRepository<SolicitationModel, UUID> {
  List<SolicitationModel> findAllBySenderResponsibleAccountSector(CollaborationSector.Sector sector);

  Optional<SolicitationModel> findById(UUID id);

  List<SolicitationModel> findAllBySenderResponsibleId(UUID userId);
}

public class JpaSolicitationsRepository implements SolicitationsRepository {

  @Autowired
  TimePunchLogAdjustmentRepository timePunchLogAdjustmentSolicitationModelsRepository;

  @Autowired
  DayOffScheduleAdjustmentRepository dayOffScheduleAdjustmentSolcitationModelsRepository;

  @Autowired
  JpaSolicitationsModelRepository solicitationsRepository;

  @Autowired
  DayOffSolicitationRepository dayOffSolicitationRepository;

  @Autowired
  PaidOvertimeSolicitationMapper paidOvertimeSolicitationMapper;

  @Autowired
  PaidOvertimeSolicitationDao paidOvertimeSolicitationDao;

  @Autowired
  ExcuseAbsenceSolicitationDao ExcuseAbsenceSolicitationDao;

  @Autowired
  ExcuseAbsenceSolicitationMapper excuseAbsenceSolicitationMapper;

  @Autowired
  SolicitationMapper mapper;

  @Override
  public void resolveSolicitation(Solicitation solicitation) {
    if (solicitation.getType().isTimePunch().isTrue()) {

      TimePunchLogAdjustmentSolicitation timePunchSolicitation = (TimePunchLogAdjustmentSolicitation) solicitation;
      timePunchLogAdjustmentSolicitationModelsRepository.resolveSolicitation(timePunchSolicitation);

    } else if (solicitation.getType().isDayOffSchedule().isTrue()) {

      DayOffScheduleAdjustmentSolicitation dayOffScheduleSolicitation = (DayOffScheduleAdjustmentSolicitation) solicitation;
      dayOffScheduleAdjustmentSolcitationModelsRepository.resolveSolicitation(dayOffScheduleSolicitation);

    } else if (solicitation.getType().isDayOff().isTrue()) {
      DayOffSolicitation dayOffSolicitation = (DayOffSolicitation) solicitation;
      dayOffSolicitationRepository.resolveSolicitation(dayOffSolicitation);
    }
  }

  @Override
  public Optional<Solicitation> findSolicitationByIdAndSolicitationType(Id solicitationId, SolicitationType type) {
    var solicitationModel = solicitationsRepository.findById(solicitationId.value());
    if (solicitationModel.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(mapper.toEntity(solicitationModel.get()));
  }

  @Override
  public Array<Solicitation> findAllByCollaboratorId(Id collaboratorId) {
    var solicitationModels = solicitationsRepository.findAllBySenderResponsibleId(collaboratorId.value());
    var solicitations = Array.createFrom(solicitationModels, mapper::toEntity);

    return solicitations;

  }

  @Override
  public Array<Solicitation> findAllByCollaboratorSector(CollaborationSector sector) {
    var solicitationModels = solicitationsRepository.findAllBySenderResponsibleAccountSector(sector.value());
    var solicitations = Array.createFrom(solicitationModels, mapper::toEntity);
    return solicitations;
  }

  @Override
  public Optional<Solicitation> findSolicitationById(Id id) {
    var solicitationModel = solicitationsRepository.findById(id.value());
    if (solicitationModel.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(mapper.toEntity(solicitationModel.get()));
  }

  @Override
  public void add(PaidOvertimeSolicitation solicitation) {
    var solicitationModel = paidOvertimeSolicitationMapper.toModel(solicitation);
    paidOvertimeSolicitationDao.save(solicitationModel);
  }

  @Override
  public void add(ExcuseAbsenceSolicitation solicitation) {
    var solicitationModel = excuseAbsenceSolicitationMapper.toModel(solicitation);
    ExcuseAbsenceSolicitationDao.save(solicitationModel);
  }
}
