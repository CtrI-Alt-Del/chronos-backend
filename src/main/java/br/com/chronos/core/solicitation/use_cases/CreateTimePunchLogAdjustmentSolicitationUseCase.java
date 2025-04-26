package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.solicitation.domain.records.SolicitationStatus.Status;
import br.com.chronos.core.solicitation.interfaces.repositories.TimePunchLogAdjustmentRepository;

public class CreateTimePunchLogAdjustmentSolicitationUseCase {

  private final TimePunchLogAdjustmentRepository solicitationsRepository;

  public CreateTimePunchLogAdjustmentSolicitationUseCase(TimePunchLogAdjustmentRepository solicitationsRepository) {
    this.solicitationsRepository = solicitationsRepository;
  }

  public TimePunchLogAdjustmentSolicitationDto execute(
      TimePunchLogAdjustmentSolicitationDto dto,
      Id collaboratorId) {
    var senderResponsibleDto = new ResponsibleAggregateDto().setId(collaboratorId.value().toString());
    dto.setSenderResponsible(senderResponsibleDto);

    var solicitation = new TimePunchLogAdjustmentSolicitation(dto);
    if (solicitation.getStatus().value() != Status.PENDING) {
      throw new ValidationException("status", "deve ser pendente");
    }
    solicitationsRepository.add(solicitation);
    return solicitation.getDto();
  }
}
