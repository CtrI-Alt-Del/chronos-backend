
package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.portal.domain.dtos.WithdrawSolicitationDto;
import br.com.chronos.core.portal.domain.entities.WithdrawSolicitation;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class CreateWithdrawSolicitationUseCase {
  private final SolicitationsRepository solicitationRepository;

  public CreateWithdrawSolicitationUseCase(SolicitationsRepository solicitationRepository) {
    this.solicitationRepository = solicitationRepository;
  }

  public WithdrawSolicitationDto execute(
      WithdrawSolicitationDto dto,
      String senderResponsibleId) {
    var senderResponsibleDto = new ResponsibleAggregateDto().setId(senderResponsibleId);
    dto.setSenderResponsible(senderResponsibleDto);
    var solicitation = new WithdrawSolicitation(dto);
    solicitationRepository.add(solicitation);
    return solicitation.getDto();
  }
}
