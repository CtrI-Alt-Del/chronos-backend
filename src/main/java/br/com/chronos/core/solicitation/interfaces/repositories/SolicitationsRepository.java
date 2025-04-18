package br.com.chronos.core.solicitation.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.domain.records.SolicitationType;

public interface SolicitationsRepository {

  Array<Solicitation> findAllByCollaboratorId(Id collaboratorId);

  Array<Solicitation> findAllByCollaboratorSector(CollaborationSector sector);

  void resolveSolicitation(Solicitation solicitation);
  Optional<Solicitation> findSolicitationByIdAndSolicitationType(Id solicitationId, SolicitationType type);

}
