package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.dtos.DayOffSolicitationDto;
import br.com.chronos.core.solicitation.domain.entities.DayOffSolicitation;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffSolicitationRepository;

public class CreateDayOffSolicitationUseCase {
  private final DayOffSolicitationRepository solicitationRepository;

  public CreateDayOffSolicitationUseCase(DayOffSolicitationRepository dayOffSolicitationRepository) {
    this.solicitationRepository = dayOffSolicitationRepository;
  }

  public DayOffSolicitationDto execute(DayOffSolicitationDto dto, Id collaboratorId) {
    // var justification = new Justification(dto.justification);
    var senderResponsibleDto = new ResponsibleDto()
        .setId(collaboratorId.value().toString());

    dto.setSenderResponsible(new ResponsibleAggregateDto(senderResponsibleDto));
    // dto.setJustification(justification.getDto());
    var solicitation = new DayOffSolicitation(dto);
    // justificationRepository.add(justification);
    solicitationRepository.add(solicitation);
    return solicitation.getDto();
  }
}
