package br.com.chronos.core.portal.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Attachment;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.Justification;

public interface JustificationRepository {

  void add(Justification justification);
  void remove(Justification justification);
  Optional<Justification> findById(Id id);
  Array<Justification> findAll();

}
