package br.com.chronos.core.modules.solicitation.interfaces.repository;


import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;

public interface TimePunchLogAdjustmentRepository {
  void add(TimePunchLogAdjustmentSolicitation solicitation);


  void resolveSolicitation(TimePunchLogAdjustmentSolicitation solicitation);

  Optional<TimePunchLogAdjustmentSolicitation> findSolicitationById(Id solicitationId);

  Array<TimePunchLogAdjustmentSolicitation> findAllByCollaboratorId(Id userId);

  Array<TimePunchLogAdjustmentSolicitation> findAllByCollaboratorSector(Sector sector);
}
