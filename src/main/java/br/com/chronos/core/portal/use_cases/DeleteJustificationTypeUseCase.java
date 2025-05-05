package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.JustificationType;
import br.com.chronos.core.portal.interfaces.repositories.JustificationTypeRepository;

public class DeleteJustificationTypeUseCase{
  private final JustificationTypeRepository repository;
  public DeleteJustificationTypeUseCase(JustificationTypeRepository justificationTypeRepository) {
    this.repository = justificationTypeRepository;
  }
  public void execute(String id) {
    var justificationType = findById(id);
    repository.remove(justificationType);
  }
  public JustificationType findById(String id) {
    var justificationType = repository.findById(Id.create(id));
    if (justificationType.isEmpty()) {
      throw new NotFoundException("Tipo de justificativa nao encontrada");
    }
    return justificationType.get();
  }
}
