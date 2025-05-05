
package br.com.chronos.core.portal.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.JustificationType;

public interface JustificationTypeRepository {

  void add(JustificationType justificationType);
  void remove(JustificationType justificationType);
  Optional<JustificationType> findById(Id id);
  void replace(JustificationType justificationType);
  Array<JustificationType> findAll();

}
