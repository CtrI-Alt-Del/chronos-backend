package br.com.chronos.core.modules.collaboration.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.modules.global.domain.records.Cpf;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.PageNumber;
import br.com.chronos.core.modules.global.domain.records.PlusInteger;
import br.com.chronos.core.modules.global.domain.records.Role;
import kotlin.Pair;

public interface CollaboratorsRepository {
  Array<Collaborator> findAllActive();

  Pair<Array<Collaborator>, PlusInteger> findMany(PageNumber page, Role.RoleName role, Sector sector, Logical isActive);

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
