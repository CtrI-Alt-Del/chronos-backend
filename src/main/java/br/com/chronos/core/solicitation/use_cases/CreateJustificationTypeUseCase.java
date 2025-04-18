package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.solicitation.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.solicitation.domain.entities.JustificationType;
import br.com.chronos.core.solicitation.interfaces.repositories.JustificationTypeRepository;

public class CreateJustificationTypeUseCase {
  private final JustificationTypeRepository justificationTypeRepository;

  public CreateJustificationTypeUseCase(JustificationTypeRepository justificationTypeRepository) {
    this.justificationTypeRepository = justificationTypeRepository;
  }

  public JustificationTypeDto execute(JustificationTypeDto dto) {
    JustificationType justificationType = new JustificationType(dto);
    justificationTypeRepository.add(justificationType);
    return justificationType.getDto();
  }
}
