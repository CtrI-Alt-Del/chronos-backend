package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.portal.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.portal.domain.entities.JustificationType;
import br.com.chronos.core.portal.interfaces.repositories.JustificationTypeRepository;

public class CreateJustificationTypeUseCase {
  private final JustificationTypeRepository justificationTypeRepository;

  public CreateJustificationTypeUseCase(JustificationTypeRepository justificationTypeRepository) {
    this.justificationTypeRepository = justificationTypeRepository;
  }

  public JustificationTypeDto execute(JustificationTypeDto dto) {
    var justificationType = new JustificationType(dto);
    justificationTypeRepository.add(justificationType);
    return justificationType.getDto();
  }
}
