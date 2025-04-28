package br.com.chronos.core.solicitation.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.entities.DayOffSolicitation;

public interface DayOffSolicitationRepository {
  void add(DayOffSolicitation solicitation);

  void resolveSolicitation(DayOffSolicitation solicitation);

  Optional<DayOffSolicitation> findSolicitationById(Id solicitationId);

}
