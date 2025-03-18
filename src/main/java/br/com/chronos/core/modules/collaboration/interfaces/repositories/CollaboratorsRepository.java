package br.com.chronos.core.modules.collaboration.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.domain.records.Cpf;
import br.com.chronos.core.modules.collaboration.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.records.Page;
import br.com.chronos.core.modules.work_schedule.domain.records.PlusInteger;
import kotlin.Pair;

public interface CollaboratorsRepository {
  void update(Collaborator collaborator);

  void add(Collaborator collaborator);

  void delete(Collaborator collaborator);

  Pair<Array<Collaborator>, PlusInteger> findMany(Page page);

  Optional<Collaborator> findCollaboratorById(Id id);

  Optional<Collaborator> findByEmail(Email email);

  Optional<Collaborator> findByCpf(Cpf cpf);

  void disable(Collaborator collaborator);

  void enable(Collaborator collaborator);

  Optional<Collaborator> findByEmailOrCpf(String email, String cpf);
}
