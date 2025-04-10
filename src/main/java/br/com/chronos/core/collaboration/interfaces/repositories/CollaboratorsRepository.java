package br.com.chronos.core.collaboration.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Cpf;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import kotlin.Pair;

public interface CollaboratorsRepository {
  Array<Collaborator> findAllActive();

  Pair<Array<Collaborator>, PlusIntegerNumber> findMany(Logical isCollaboratorActive, PageNumber page);

  Pair<Array<Collaborator>, PlusIntegerNumber> findManyByCollaborationSector(
      PageNumber page,
      CollaborationSector sector,
      Logical isActive);

  Optional<Collaborator> findById(Id id);

  Optional<Collaborator> findByEmail(Email email);

  Optional<Collaborator> findByCpf(Cpf cpf);

  void update(Collaborator collaborator);

  void add(Collaborator collaborator);

  void addMany(Array<Collaborator> collaborators);

  void delete(Collaborator collaborator);

  void disable(Collaborator collaborator);

  void enable(Collaborator collaborator);

  Optional<Collaborator> findByEmailOrCpf(String email, String cpf);
}
