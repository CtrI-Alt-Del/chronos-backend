
package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.portal.domain.dtos.WorkLeaveSolicitationDto;
import br.com.chronos.core.portal.domain.entities.WorkleaveSolicitation;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.work_schedule.use_cases.CreateSolicitationUseCase;

public class CreateWithdrawSolicitationUseCase extends CreateSolicitationUseCase {
  private final SolicitationsRepository repository;

  public CreateWithdrawSolicitationUseCase(SolicitationsRepository repository, PortalBroker broker) {
    super(broker);
    this.repository = repository;
  }

  public WorkLeaveSolicitationDto execute(
      WorkLeaveSolicitationDto dto,
      String senderResponsibleId) {
    var senderResponsibleDto = new ResponsibleAggregateDto().setId(senderResponsibleId);
    dto.setSenderResponsible(senderResponsibleDto);
    var solicitation = new WorkleaveSolicitation(dto);
    repository.add(solicitation);
    return solicitation.getDto();
  }
}
