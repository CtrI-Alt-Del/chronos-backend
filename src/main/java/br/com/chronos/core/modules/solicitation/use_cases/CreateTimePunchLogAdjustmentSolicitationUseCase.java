package br.com.chronos.core.modules.solicitation.use_cases;

import br.com.chronos.core.modules.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;

public class CreateTimePunchLogAdjustmentSolicitationUseCase {

    private final SolicitationsRepository repository;

    public CreateTimePunchLogAdjustmentSolicitationUseCase(SolicitationsRepository repository) {
        this.repository = repository;
    }

    public TimePunchLogAdjustmentSolicitationDto execute(TimePunchLogAdjustmentSolicitationDto dto) {
        var solicitation = new TimePunchLogAdjustmentSolicitation(dto);
        repository.addTimePunchLogAdjustmentSolicitation(solicitation);
        return solicitation.getDto();
    }


}
