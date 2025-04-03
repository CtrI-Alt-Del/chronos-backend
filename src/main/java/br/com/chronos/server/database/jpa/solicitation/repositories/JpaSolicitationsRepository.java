package br.com.chronos.server.database.jpa.solicitation.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Role;
import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.interfaces.repository.DayOffScheduleAdjustmentRepository;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.solicitation.interfaces.repository.TimePunchLogAdjustmentRepository;

public class JpaSolicitationsRepository implements SolicitationsRepository {

  @Autowired
  TimePunchLogAdjustmentRepository timePunchLogAdjustmentSolicitationModelsRepository;

  @Autowired
  DayOffScheduleAdjustmentRepository dayOffScheduleAdjustmentSolcitationModelsRepository;

  @Override
  @Transactional(readOnly = true)
  public Array<Solicitation> findMany(Sector sector, Role role, Id userId) {
    List<TimePunchLogAdjustmentSolicitation> timePunchSolicitations;
    List<DayOffScheduleAdjustmentSolicitation> dayOffScheduleSolicitations;

    if (role.isEmployee().isTrue()) {
      timePunchSolicitations = timePunchLogAdjustmentSolicitationModelsRepository
          .findAllByCollaboratorId(userId).list();

      dayOffScheduleSolicitations = dayOffScheduleAdjustmentSolcitationModelsRepository
          .findAllByCollaboratorId(userId).list();
    } else {

      timePunchSolicitations = timePunchLogAdjustmentSolicitationModelsRepository
          .findAllByCollaboratorSector(sector).list();

      dayOffScheduleSolicitations = dayOffScheduleAdjustmentSolcitationModelsRepository
          .findAllByCollaboratorSector(sector).list();
    }

    var allSolicitations = new ArrayList<Solicitation>();
    allSolicitations.addAll(timePunchSolicitations);
    allSolicitations.addAll(dayOffScheduleSolicitations);

    return new Array<>(allSolicitations);
  }

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
  public Optional<Solicitation> findSolicitationById(Id solicitationId) {
    var timePunchSolicitation = timePunchLogAdjustmentSolicitationModelsRepository.findSolicitationById(solicitationId);
    if (timePunchSolicitation.isPresent()) {
      return Optional.of(timePunchSolicitation.get());
    }

    var dayOffScheduleSolicitation = dayOffScheduleAdjustmentSolcitationModelsRepository
        .findSolicitationById(solicitationId);
    if (dayOffScheduleSolicitation.isPresent()) {
      return Optional.of(dayOffScheduleSolicitation.get());
    }

    return Optional.empty();
  }
}
