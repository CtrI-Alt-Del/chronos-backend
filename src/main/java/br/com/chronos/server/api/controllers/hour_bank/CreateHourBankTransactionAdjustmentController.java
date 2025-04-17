package br.com.chronos.server.api.controllers.hour_bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.CreateHourBankTransactionAdjustmentUseCase;

@HourBankController
public class CreateHourBankTransactionAdjustmentController {
  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  @PostMapping("/{collaboratorId}/transactions/adjustment")
  public ResponseEntity<Void> handle(
      @PathVariable String collaboratorId,
      @RequestBody HourBankTransactionDto hourBankTransactionDto) {
    var useCase = new CreateHourBankTransactionAdjustmentUseCase(hourBankTransactionsRepository);
    useCase.execute(hourBankTransactionDto, collaboratorId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
