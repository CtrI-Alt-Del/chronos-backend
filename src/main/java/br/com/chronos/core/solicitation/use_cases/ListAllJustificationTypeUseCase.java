package br.com.chronos.core.solicitation.use_cases;

import java.util.List;

import br.com.chronos.core.solicitation.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.solicitation.interfaces.repositories.JustificationTypeRepository;

public class ListAllJustificationTypeUseCase {
  private final JustificationTypeRepository justificationTypeRepository;

  public ListAllJustificationTypeUseCase(JustificationTypeRepository justificationTypeRepository) {
    this.justificationTypeRepository = justificationTypeRepository;
  }
  public List<JustificationTypeDto> execute() {
    var justificationTypes = justificationTypeRepository.findAll();
    return justificationTypes.map(justificationType -> justificationType.getDto()).list();
  }
}
