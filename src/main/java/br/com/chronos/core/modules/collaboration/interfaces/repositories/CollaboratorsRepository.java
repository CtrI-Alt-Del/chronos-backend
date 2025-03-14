package br.com.chronos.core.modules.collaboration.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.global.domain.records.Id;

public interface CollaboratorsRepository {
  void update(Collaborator collaborator);
  void add(Collaborator collaborator);
  void delete(Collaborator collaborator);
  Optional<Collaborator> findById(Id id);
}
