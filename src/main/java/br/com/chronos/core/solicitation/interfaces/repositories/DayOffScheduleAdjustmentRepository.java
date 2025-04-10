
package br.com.chronos.core.solicitation.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.entities.DayOffScheduleAdjustmentSolicitation;

public interface DayOffScheduleAdjustmentRepository {
  void add(DayOffScheduleAdjustmentSolicitation solicitation);

  void resolveSolicitation(DayOffScheduleAdjustmentSolicitation solicitation);

  Optional<DayOffScheduleAdjustmentSolicitation> findSolicitationById(Id solicitationId);

  Array<DayOffScheduleAdjustmentSolicitation> findAllByCollaboratorId(Id userId);

  Array<DayOffScheduleAdjustmentSolicitation> findAllByCollaboratorSector(CollaborationSector sector);

}
