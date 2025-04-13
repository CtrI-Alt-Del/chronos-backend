package br.com.chronos.server.database.jpa.solicitation.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffScheduleAdjustmentRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.server.database.jpa.solicitation.mappers.DayOffScheduleAdjustmentSolicitationMapper;
import br.com.chronos.server.database.jpa.solicitation.models.DayOffScheduleAdjustmentSolicitationModel;
import br.com.chronos.server.database.jpa.work_schedule.mappers.DayOffMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.DayOffModel;

interface JpaDayOffScheduleAdjustmentRepositorySolicitation
    extends JpaRepository<DayOffScheduleAdjustmentSolicitationModel, UUID> {
  List<DayOffScheduleAdjustmentSolicitationModel> findAllBySenderResponsibleAccountSector(Sector sector);

  List<DayOffScheduleAdjustmentSolicitationModel> findAllBySenderResponsibleId(
      UUID userId);

}

interface JpaAdjustmentDayOffScheduleModelsRepository extends JpaRepository<DayOffModel, UUID> {

}

public class JpaDayOffScheduleAdjustmentSolicitationsRepository implements DayOffScheduleAdjustmentRepository {

  @Autowired
  private JpaDayOffScheduleAdjustmentRepositorySolicitation solicitationRepository;
  @Autowired
  private JpaAdjustmentDayOffScheduleModelsRepository dayOffModelsRepository;

  @Autowired
  private DayOffScheduleAdjustmentSolicitationMapper solicitationMapper;

  @Autowired
  private DayOffSchedulesRepository dayOffSchedulesRepository;

  @Autowired
  private DayOffMapper dayOffMapper;

  @Override
  public void add(DayOffScheduleAdjustmentSolicitation solicitation) {
    var dayOffScheduleAdjustmentSolicitationModel = solicitationMapper.toModel(solicitation);
    var dayOffModels = solicitation.getDayOffSchedule().getDaysOff().map((dayOff) -> {
      var dayOffModel = dayOffMapper.toModel(dayOff);
      dayOffModel.setDayOffScheduleAdjustment(dayOffScheduleAdjustmentSolicitationModel);
      return dayOffModel;
    });
    solicitationRepository.save(dayOffScheduleAdjustmentSolicitationModel);
    dayOffModelsRepository.saveAll(dayOffModels.list());

  }

  public void resolveSolicitation(DayOffScheduleAdjustmentSolicitation solicitation) {
    var solicitationModel = solicitationMapper.toModel(solicitation);
    solicitationModel.setSolicitationStatus(solicitation.getStatus().value());

    if (solicitation.getStatus().isApproved().isTrue()) {
      var collaboratorId = solicitation.getSenderResponsible().getId();
      var newDayOffSchedule = solicitation.getDayOffSchedule();
      dayOffSchedulesRepository.replace(newDayOffSchedule, collaboratorId);
    }

    solicitationRepository.save(solicitationModel);
  }

  @Override
  public Optional<DayOffScheduleAdjustmentSolicitation> findSolicitationById(Id solicitationId) {
    return solicitationRepository.findById(solicitationId.value())
        .map(solicitationMapper::toEntity);
  }

  @Override
  public Array<DayOffScheduleAdjustmentSolicitation> findAllByCollaboratorId(Id userId) {
    var solicitationModels = solicitationRepository.findAllBySenderResponsibleId(userId.value());
    return Array.createFrom(solicitationModels, solicitationMapper::toEntity);

  }

  @Override
  public Array<DayOffScheduleAdjustmentSolicitation> findAllByCollaboratorSector(CollaborationSector sector) {
    var solicitationModels = solicitationRepository.findAllBySenderResponsibleAccountSector(sector.value());
    return Array.createFrom(solicitationModels, solicitationMapper::toEntity);
  }
}
