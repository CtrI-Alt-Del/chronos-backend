package br.com.chronos.core.solicitation.interfaces.repository;

import br.com.chronos.core.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.solicitation.domain.entities.WorkScheduleAdjustmentSolicitation;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Role;

public interface SolicitationsRepository {
  void addTimePunchLogAdjustmentSolicitation(TimePunchLogAdjustmentSolicitation solicitation);

  void addWorkScheduleAdjustmentSolicitation(WorkScheduleAdjustmentSolicitation solicitation);

  Array<Solicitation> findMany(Sector sector, Role role, Id userId);

  void resolveSolicitation(Solicitation solicitation);

  Optional<Solicitation> findSolicitationById(Id solicitationId);
}
