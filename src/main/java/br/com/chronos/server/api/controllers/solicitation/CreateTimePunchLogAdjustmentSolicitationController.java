package br.com.chronos.server.api.controllers.solicitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.modules.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.core.modules.solicitation.use_cases.CreateTimePunchLogAdjustmentSolicitationUseCase;

@SolicitationsController
public class CreateTimePunchLogAdjustmentSolicitationController {

  @Autowired
  private SolicitationsRepository solicitationsRepository;

    @PostMapping("/time-punch-adjustment")
    public ResponseEntity<TimePunchLogAdjustmentSolicitationDto> handle(
            @RequestBody TimePunchLogAdjustmentSolicitationDto body) {
        var useCase = new CreateTimePunchLogAdjustmentSolicitationUseCase(solicitationsRepository);
        var timePunchLogAdjustmentSolicitationDto = useCase.execute(body);
        return ResponseEntity.ok(timePunchLogAdjustmentSolicitationDto);
    }
}
