package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.solicitation.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.solicitation.domain.entities.JustificationType;
import br.com.chronos.core.solicitation.interfaces.repositories.JustificationTypeRepository;

public class UpdateJustificationTypeUseCase {
  private final JustificationTypeRepository justificationTypeRepository;

  public UpdateJustificationTypeUseCase(JustificationTypeRepository justificationTypeRepository) {
    this.justificationTypeRepository = justificationTypeRepository;
  }

  public void execute(String id, JustificationTypeDto justificationTypeDto) {
    var justificationType = findById(Id.create(id));
    if (justificationTypeDto.name != null) {
      justificationType.updateName(Text.create(justificationTypeDto.name, justificationTypeDto.name));
    }
    if (justificationTypeDto.shouldHaveAttachment != null) {
      justificationType.updateShouldHaveAttachment(Logical.create(justificationTypeDto.shouldHaveAttachment));
    }
    justificationTypeRepository.replace(justificationType);
  }

  public JustificationType findById(Id id) {
    var justificationType = justificationTypeRepository.findById(id);
    if (justificationType.isEmpty()) {
      throw new NotFoundException("Tipo de justificativa nao encontrada");
    }
    return justificationType.get();
  }
}
