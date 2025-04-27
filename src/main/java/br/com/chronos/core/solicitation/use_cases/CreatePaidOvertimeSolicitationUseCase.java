package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.solicitation.domain.dtos.PaidOvertimeSolicitationDto;
import br.com.chronos.core.solicitation.domain.entities.PaidOvertimeSolicitation;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;

public class CreatePaidOvertimeSolicitationUseCase {
  private final SolicitationsRepository repository;

  public CreatePaidOvertimeSolicitationUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public void execute(PaidOvertimeSolicitationDto dto, String senderResponsibleId) {
    var senderResponsibleDto = new ResponsibleAggregateDto().setId(senderResponsibleId);
    dto.setSenderResponsible(senderResponsibleDto);
    var solicitation = new PaidOvertimeSolicitation(dto);
    repository.add(solicitation);
  }
}
