package br.com.chronos.core.solicitation.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.domain.entities.PaidOvertimeSolicitation;
import br.com.chronos.core.solicitation.domain.records.SolicitationType;
import kotlin.Pair;

public interface SolicitationsRepository {
  Optional<Solicitation> findById(Id id);

  Optional<PaidOvertimeSolicitation> findPaidOvertimeSolicitationById(Id id);

  Array<Solicitation> findAllByCollaboratorId(Id collaboratorId);

  Pair<Array<PaidOvertimeSolicitation>, PlusIntegerNumber> findManyPaidOvertimeSolicitationsByCollaborationSector(
      CollaborationSector sector,
      PageNumber page);

  Array<Solicitation> findAllByCollaboratorSector(CollaborationSector sector);

  void resolveSolicitation(Solicitation solicitation);

  Optional<Solicitation> findSolicitationByIdAndSolicitationType(Id solicitationId, SolicitationType type);

  void add(PaidOvertimeSolicitation solicitation);

  void replace(Solicitation solicitation);

  void replace(PaidOvertimeSolicitation solicitation);
}
