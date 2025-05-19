package br.com.chronos.core.portal.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.portal.domain.dtos.DayOffSolicitationDto;
import br.com.chronos.core.portal.domain.entities.DayOffSolicitation;
import br.com.chronos.core.portal.interfaces.PortalBroker;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.work_schedule.use_cases.CreateSolicitationUseCase;

public class CreateDayOffSolicitationUseCase extends CreateSolicitationUseCase {
  private final SolicitationsRepository repository;

  public CreateDayOffSolicitationUseCase(SolicitationsRepository repository, PortalBroker broker) {
    super(broker);
    this.repository = repository;
  }

  public DayOffSolicitationDto execute(
      DayOffSolicitationDto dto,
      String senderResponsibleId,
      String collaboratorionSector) {
    var responsibleDto = new ResponsibleDto()
        .setId(senderResponsibleId)
        .setSector(collaboratorionSector);
    var senderResponsibleDto = new ResponsibleAggregateDto(responsibleDto);

    dto.setSenderResponsible(senderResponsibleDto);
    if (dto.dayOff.isBefore(LocalDate.now())) {
      throw new ValidationException(
          "data de solicitação",
          "A data da solicitação não pode ser anterior a data atual");
    }
    var solicitation = new DayOffSolicitation(dto);
    repository.add(solicitation);

    sendSolicitationCreatedEvent(solicitation);
    return solicitation.getDto();
  }
}
