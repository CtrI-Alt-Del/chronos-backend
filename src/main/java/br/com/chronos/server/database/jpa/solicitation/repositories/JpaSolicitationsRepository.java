package br.com.chronos.server.database.jpa.solicitation.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.domain.records.SolicitationType;
import br.com.chronos.core.modules.solicitation.interfaces.repository.DayOffScheduleAdjustmentRepository;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.solicitation.interfaces.repository.TimePunchLogAdjustmentRepository;

public class JpaSolicitationsRepository implements SolicitationsRepository {

  @Autowired
  TimePunchLogAdjustmentRepository timePunchLogAdjustmentSolicitationModelsRepository;

  @Autowired
  DayOffScheduleAdjustmentRepository dayOffScheduleAdjustmentSolcitationModelsRepository;

  @Override
  public void resolveSolicitation(Solicitation solicitation) {
    if (solicitation instanceof TimePunchLogAdjustmentSolicitation) {
      TimePunchLogAdjustmentSolicitation timePunchSolicitation = (TimePunchLogAdjustmentSolicitation) solicitation;

      timePunchLogAdjustmentSolicitationModelsRepository.resolveSolicitation(timePunchSolicitation);

    } else if (solicitation instanceof DayOffScheduleAdjustmentSolicitation) {
      DayOffScheduleAdjustmentSolicitation dayOffScheduleSolicitation = (DayOffScheduleAdjustmentSolicitation) solicitation;
      dayOffScheduleAdjustmentSolcitationModelsRepository.resolveSolicitation(dayOffScheduleSolicitation);
    }
  }

  @Override
  public Optional<Solicitation> findSolicitationByIdAndSolicitationType(Id solicitationId, SolicitationType type) {
    if (type.isTimePunch().isTrue()) {
      var solicitation = timePunchLogAdjustmentSolicitationModelsRepository
          .findSolicitationById(solicitationId);
      return Optional.of(solicitation.get());
    } else {
      var solicitation = dayOffScheduleAdjustmentSolcitationModelsRepository
          .findSolicitationById(solicitationId);
      return Optional.of(solicitation.get());
    }
  }

  @Override
  public Array<Solicitation> findAllByCollaboratorId(Id collaboratorId) {
    List<TimePunchLogAdjustmentSolicitation> timePunchSolicitations;
    List<DayOffScheduleAdjustmentSolicitation> dayOffScheduleSolicitations;
    timePunchSolicitations = timePunchLogAdjustmentSolicitationModelsRepository
        .findAllByCollaboratorId(collaboratorId).list();

    dayOffScheduleSolicitations = dayOffScheduleAdjustmentSolcitationModelsRepository
        .findAllByCollaboratorId(collaboratorId).list();
    var allSolicitations = new ArrayList<Solicitation>();
    allSolicitations.addAll(timePunchSolicitations);
    allSolicitations.addAll(dayOffScheduleSolicitations);

    return new Array<>(allSolicitations);

  }

  @Override
  public Array<Solicitation> findAllByCollaboratorSector(CollaborationSector sector) {
    List<TimePunchLogAdjustmentSolicitation> timePunchSolicitations;
    List<DayOffScheduleAdjustmentSolicitation> dayOffScheduleSolicitations;

    timePunchSolicitations = timePunchLogAdjustmentSolicitationModelsRepository
        .findAllByCollaboratorSector(sector).list();

    dayOffScheduleSolicitations = dayOffScheduleAdjustmentSolcitationModelsRepository
        .findAllByCollaboratorSector(sector).list();

    var allSolicitations = new ArrayList<Solicitation>();
    allSolicitations.addAll(timePunchSolicitations);
    allSolicitations.addAll(dayOffScheduleSolicitations);

    return new Array<>(allSolicitations);
  }
}
