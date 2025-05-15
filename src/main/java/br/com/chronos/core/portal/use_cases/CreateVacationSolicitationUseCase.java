package br.com.chronos.core.portal.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.domain.dtos.VacationSolicitationDto;
import br.com.chronos.core.portal.domain.entities.VacationSolicitation;

public class CreateVacationSolicitationUseCase {
    
    private final SolicitationsRepository repository;

    public CreateVacationSolicitationUseCase(SolicitationsRepository repository) {
        this.repository = repository;
    }

    public VacationSolicitationDto execute (VacationSolicitationDto dto, String senderResponsibleId) {
        var senderResponsibleDto = new ResponsibleAggregateDto().setId(senderResponsibleId);
        dto.setSenderResponsible(senderResponsibleDto);
        dto.setDate(LocalDate.now());
        validateVacationSolicitation(dto);
        var solicitation = new VacationSolicitation(dto);
        repository.add(solicitation);
        return solicitation.getDto();
    }

    private void validateVacationSolicitation(VacationSolicitationDto dto) {
        if(dto.vacationDays == null || dto.vacationDays.isEmpty()) {
            throw new ValidationException("Dados Inválidos", "A lista de duas férias não pode ser nula ou vazia");
        }

        LocalDate today = LocalDate.now();

        for (LocalDate vacationDay : dto.vacationDays) {
            if (vacationDay.isBefore(today)) {
                throw new ValidationException("Data inválida", "A data da solicitação não pode ser anterior a data atual");
            }
        }
    }

    
}
