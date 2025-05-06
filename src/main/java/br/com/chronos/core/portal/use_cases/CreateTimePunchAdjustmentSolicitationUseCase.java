package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.portal.domain.dtos.TimePunchAdjustmentSolicitationDto;
import br.com.chronos.core.portal.domain.entities.TimePunchAdjustmentSolicitation;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class CreateTimePunchAdjustmentSolicitationUseCase {
  private final SolicitationsRepository repository;

  public CreateTimePunchAdjustmentSolicitationUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public TimePunchAdjustmentSolicitationDto execute(
      TimePunchAdjustmentSolicitationDto dto,
      String senderResponsibleId) {
    var senderResponsibleDto = new ResponsibleAggregateDto().setId(senderResponsibleId);
    dto.setSenderResponsible(senderResponsibleDto);
    var solicitation = new TimePunchAdjustmentSolicitation(dto);
    repository.add(solicitation);
    return solicitation.getDto();
  }
}
