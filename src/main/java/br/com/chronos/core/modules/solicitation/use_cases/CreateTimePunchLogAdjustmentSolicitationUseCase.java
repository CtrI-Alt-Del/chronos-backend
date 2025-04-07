package br.com.chronos.core.modules.solicitation.use_cases;

import br.com.chronos.core.modules.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.domain.records.SolicitationStatus.Status;
import br.com.chronos.core.modules.solicitation.interfaces.repository.TimePunchLogAdjustmentRepository;

public class CreateTimePunchLogAdjustmentSolicitationUseCase {

  private final TimePunchLogAdjustmentRepository solicitationsRepository;

  public CreateTimePunchLogAdjustmentSolicitationUseCase(TimePunchLogAdjustmentRepository solicitationsRepository) {
    this.solicitationsRepository = solicitationsRepository;
  }

  public TimePunchLogAdjustmentSolicitationDto execute(TimePunchLogAdjustmentSolicitationDto dto, Id collaboratorId) {
    var senderResponsibleDto = new ResponsibleDto()
        .setId(collaboratorId.value().toString());
    dto.setSenderResponsible(new ResponsibleAggregateDto(senderResponsibleDto));
    var solicitation = new TimePunchLogAdjustmentSolicitation(dto);
    if (solicitation.status.value() != Status.PENDING) {
      throw new ValidationException("status", "deve ser pendente");
    }
    solicitationsRepository.add(solicitation);
    return solicitation.getDto();
  }
}
