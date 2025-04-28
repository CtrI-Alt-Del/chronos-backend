package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.solicitation.interfaces.repositories.TimePunchLogAdjustmentRepository;

public class CreateTimePunchLogAdjustmentSolicitationUseCase {

  private final TimePunchLogAdjustmentRepository solicitationsRepository;

  public CreateTimePunchLogAdjustmentSolicitationUseCase(TimePunchLogAdjustmentRepository solicitationsRepository) {
    this.solicitationsRepository = solicitationsRepository;
  }

  public TimePunchLogAdjustmentSolicitationDto execute(
      TimePunchLogAdjustmentSolicitationDto dto,
      String senderResponsibleId) {
    var senderResponsibleDto = new ResponsibleAggregateDto().setId(senderResponsibleId);
    dto.setSenderResponsible(senderResponsibleDto);
    var solicitation = new TimePunchLogAdjustmentSolicitation(dto);
    solicitationsRepository.add(solicitation);
    return solicitation.getDto();
  }
}
