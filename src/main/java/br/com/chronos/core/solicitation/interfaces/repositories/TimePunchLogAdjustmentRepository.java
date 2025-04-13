package br.com.chronos.core.solicitation.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;

public interface TimePunchLogAdjustmentRepository {
  void add(TimePunchLogAdjustmentSolicitation solicitation);

  void resolveSolicitation(TimePunchLogAdjustmentSolicitation solicitation);

  Optional<TimePunchLogAdjustmentSolicitation> findSolicitationById(Id solicitationId);

  Array<TimePunchLogAdjustmentSolicitation> findAllByCollaboratorId(Id userId);

  Array<TimePunchLogAdjustmentSolicitation> findAllByCollaboratorSector(CollaborationSector sector);
}
