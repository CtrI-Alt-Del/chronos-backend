package br.com.chronos.core.solicitation.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.exceptions.ValidationException;
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
    if (dto.dayOff.isBefore(LocalDate.now())) {
      throw new ValidationException("Data inválida", "A data da solicitação não pode ser anterior a data atual");
    }
    var solicitation = new DayOffSolicitation(dto);
    repository.add(solicitation);
    return solicitation.getDto();
  }
}
