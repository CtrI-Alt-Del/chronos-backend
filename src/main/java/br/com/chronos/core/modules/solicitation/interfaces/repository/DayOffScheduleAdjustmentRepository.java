
package br.com.chronos.core.modules.solicitation.interfaces.repository;

import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.solicitation.domain.entities.DayOffScheduleAdjustmentSolicitation;

public interface DayOffScheduleAdjustmentRepository {
  void add(DayOffScheduleAdjustmentSolicitation solicitation);

  void resolveSolicitation(DayOffScheduleAdjustmentSolicitation solicitation);

  Optional<DayOffScheduleAdjustmentSolicitation> findSolicitationById(Id solicitationId);

  Array<DayOffScheduleAdjustmentSolicitation> findAllByCollaboratorId(Id userId);

  Array<DayOffScheduleAdjustmentSolicitation> findAllByCollaboratorSector(Sector sector);

}
