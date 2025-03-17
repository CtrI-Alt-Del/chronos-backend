package br.com.chronos.core.modules.collaboration.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import kotlin.Pair;

public interface CollaboratorsRepository {
  void update(Collaborator collaborator);
  void add(Collaborator collaborator);
  void delete(Collaborator collaborator);
  Pair<Array<Collaborator>, Long> findMany(int page,int itemsPerPage);
  Optional<Collaborator> findCollaboratorById(Id id);
}
