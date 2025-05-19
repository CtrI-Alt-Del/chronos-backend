
package br.com.chronos.core.portal.use_cases;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.portal.domain.dtos.ExcusedAbsenceSolicitationDto;
import br.com.chronos.core.portal.domain.entities.ExcusedAbsenceSolicitation;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.work_schedule.use_cases.CreateSolicitationUseCase;

public class CreateExcusedAbsenceSolicitationUseCase extends CreateSolicitationUseCase {
  private final SolicitationsRepository solicitationRepository;

  public CreateExcusedAbsenceSolicitationUseCase(
      SolicitationsRepository solicitationRepository,
      PortalBroker broker) {
    super(broker);
    this.solicitationRepository = solicitationRepository;
  }

  public ExcusedAbsenceSolicitationDto execute(
      ExcusedAbsenceSolicitationDto dto,
      String senderResponsibleId,
      String collaboratorionSector) {
    var responsibleDto = new ResponsibleDto()
        .setId(senderResponsibleId)
        .setSector(collaboratorionSector);
    var senderResponsibleDto = new ResponsibleAggregateDto(responsibleDto);

    dto.setSenderResponsible(senderResponsibleDto);
    var solicitation = new ExcusedAbsenceSolicitation(dto);
    solicitationRepository.add(solicitation);

    sendSolicitationCreatedEvent(solicitation);
    return solicitation.getDto();
  }
}
