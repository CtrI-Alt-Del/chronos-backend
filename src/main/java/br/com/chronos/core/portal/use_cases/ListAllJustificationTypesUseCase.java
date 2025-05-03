package br.com.chronos.core.portal.use_cases;

import java.util.List;

import br.com.chronos.core.portal.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.portal.interfaces.repositories.JustificationTypeRepository;

public class ListAllJustificationTypesUseCase {
  private final JustificationTypeRepository justificationTypeRepository;

  public ListAllJustificationTypesUseCase(JustificationTypeRepository justificationTypeRepository) {
    this.justificationTypeRepository = justificationTypeRepository;
  }
  public List<JustificationTypeDto> execute() {
    var justificationTypes = justificationTypeRepository.findAll();
    return justificationTypes.map(justificationType -> justificationType.getDto()).list();
  }
}
