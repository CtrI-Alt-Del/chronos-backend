
package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.portal.domain.dtos.ExcusedAbsenceSolicitationDto;
import br.com.chronos.core.portal.domain.entities.ExcusedAbsenceSolicitation;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

public class CreateExcusedAbsenceSolicitationUseCase {
  private final SolicitationsRepository solicitationRepository;

  public CreateExcusedAbsenceSolicitationUseCase(SolicitationsRepository solicitationRepository) {
    this.solicitationRepository = solicitationRepository;
  }

  public ExcusedAbsenceSolicitationDto execute(
      ExcusedAbsenceSolicitationDto dto,
      String senderResponsibleId) {
    var senderResponsibleDto = new ResponsibleAggregateDto().setId(senderResponsibleId);

    dto.setSenderResponsible(senderResponsibleDto);
    var solicitation = new ExcusedAbsenceSolicitation(dto);
    solicitationRepository.add(solicitation);
    return solicitation.getDto();
  }
}
