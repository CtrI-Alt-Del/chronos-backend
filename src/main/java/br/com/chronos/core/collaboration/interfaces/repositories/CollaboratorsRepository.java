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
      CollaborationSector sector,
      Logical isActive,
      PageNumber page);

  Optional<Collaborator> findById(Id id);

  Optional<Collaborator> findByEmail(Email email);

  Optional<Collaborator> findByCpf(Cpf cpf);

  Optional<Collaborator> findByEmailOrCpf(String email, String cpf);

  void add(Collaborator collaborator);

  void addMany(Array<Collaborator> collaborators);

  void replace(Collaborator collaborator);

  void remove(Collaborator collaborator);

}
