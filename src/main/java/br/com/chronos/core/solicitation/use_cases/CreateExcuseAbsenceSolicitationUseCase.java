
package br.com.chronos.core.solicitation.use_cases;

import java.util.logging.Logger;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.dtos.ExcuseAbsenceSolicitationDto;
import br.com.chronos.core.solicitation.domain.entities.ExcuseAbsenceSolicitation;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;

public class CreateExcuseAbsenceSolicitationUseCase {
  private final SolicitationsRepository solicitationRepository;

  public CreateExcuseAbsenceSolicitationUseCase(SolicitationsRepository solicitationRepository) {
    this.solicitationRepository = solicitationRepository;
  }

  public ExcuseAbsenceSolicitationDto execute(ExcuseAbsenceSolicitationDto dto, Id collaboratorId) {
    var senderResponsibleDto = new ResponsibleDto()
        .setId(collaboratorId.value().toString());

    dto.setSenderResponsible(new ResponsibleAggregateDto(senderResponsibleDto));
    var solicitation = new ExcuseAbsenceSolicitation(dto);
    solicitationRepository.add(solicitation);
    return solicitation.getDto();
  }
}
