package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.solicitation.domain.dtos.DayOffSolicitationDto;
import br.com.chronos.core.solicitation.domain.entities.DayOffSolicitation;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;

public class CreateDayOffSolicitationUseCase {
  private final SolicitationsRepository repository;

  public CreateDayOffSolicitationUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public DayOffSolicitationDto execute(DayOffSolicitationDto dto, String senderResponsibleId) {
    var senderResponsibleDto = new ResponsibleAggregateDto().setId(senderResponsibleId);
    dto.setSenderResponsible(senderResponsibleDto);
    var solicitation = new DayOffSolicitation(dto);
    repository.add(solicitation);
    return solicitation.getDto();
  }
}
