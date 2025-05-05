package br.com.chronos.server.database.jpa.portal.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.portal.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.portal.interfaces.repositories.TimePunchLogAdjustmentRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.server.database.jpa.portal.mappers.TimePunchLogAdjustmentSolicitationMapper;
import br.com.chronos.server.database.jpa.portal.models.TimePunchLogAdjustmentSolicitationModel;

interface JpaTimePunchAdjustmentSolicitationRepository
    extends JpaRepository<TimePunchLogAdjustmentSolicitationModel, UUID> {
  List<TimePunchLogAdjustmentSolicitationModel> findAllBySenderResponsibleAccountSector(Sector sector);

  List<TimePunchLogAdjustmentSolicitationModel> findAllBySenderResponsibleId(
      UUID userId);
}

public class JpaTimePunchLogPunchAdjustmentSolicitationsRepository implements TimePunchLogAdjustmentRepository {

  @Autowired
  private JpaTimePunchAdjustmentSolicitationRepository solicitationRepository;

  @Autowired
  private TimePunchLogAdjustmentSolicitationMapper mapper;

  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Override
  public void add(TimePunchLogAdjustmentSolicitation solicitation) {
    var solicitationModel = mapper.toModel(solicitation);
    solicitationModel.setSolicitationStatus(solicitation.getStatus().value());
    if (solicitation.getStatus().isApproved().isTrue()) {
      var wordaylog = workdayLogsRepository.findByCollaboratorAndDate(
          solicitation.getSenderResponsible().getId(), Date.create(solicitation.getDate().value())).get();
      wordaylog.getTimePunch().adjust(solicitation.getTime(), solicitation.getPeriod());
      workdayLogsRepository.replace(wordaylog);
    }
    solicitationRepository.save(solicitationModel);
  }

  public void resolveSolicitation(TimePunchLogAdjustmentSolicitation solicitation) {
    var solicitationModel = mapper.toModel(solicitation);
    solicitationModel.setSolicitationStatus(solicitation.getStatus().value());
    solicitationRepository.save(solicitationModel);
  }

  @Override
  public Optional<TimePunchLogAdjustmentSolicitation> findSolicitationById(Id solicitationId) {
    return solicitationRepository.findById(solicitationId.value())
        .map(mapper::toEntity);
  }

  @Override
  public Array<TimePunchLogAdjustmentSolicitation> findAllByCollaboratorId(Id userId) {
    var solicitationModels = solicitationRepository.findAllBySenderResponsibleId(userId.value());
    return Array.createFrom(solicitationModels, mapper::toEntity);
  }

  @Override
  public Array<TimePunchLogAdjustmentSolicitation> findAllByCollaboratorSector(CollaborationSector sector) {
    var solicitationModels = solicitationRepository.findAllBySenderResponsibleAccountSector(sector.value());
    return Array.createFrom(solicitationModels, mapper::toEntity);
  }
}
