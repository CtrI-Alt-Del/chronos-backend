package br.com.chronos.core.portal.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.TimePunchAdjustmentSolicitation;

public interface TimePunchLogAdjustmentRepository {
  void add(TimePunchAdjustmentSolicitation solicitation);

  void resolveSolicitation(TimePunchAdjustmentSolicitation solicitation);

  Optional<TimePunchAdjustmentSolicitation> findSolicitationById(Id solicitationId);

  Array<TimePunchAdjustmentSolicitation> findAllByCollaboratorId(Id userId);

  Array<TimePunchAdjustmentSolicitation> findAllByCollaboratorSector(CollaborationSector sector);
}
