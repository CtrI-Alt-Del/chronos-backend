package br.com.chronos.core.modules.solicitation.interfaces.repository;


import java.util.Optional;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.records.SolicitationType;

public interface SolicitationsRepository {


  Array<Solicitation> findAllByCollaboratorId(Id collaboratorId);
  Array<Solicitation> findAllByCollaboratorSector(CollaborationSector sector);

  void resolveSolicitation(Solicitation solicitation);

  Optional<Solicitation> findSolicitationByIdAndSolicitationType(Id solicitationId,SolicitationType type);

}
