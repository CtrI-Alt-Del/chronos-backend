package br.com.chronos.server.api.controllers.hour_bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankBalanceDto;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.CalculateHourBankBalanceUseCase;

@HourBankController
public class CalculateHourBankBalanceController {
  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  @GetMapping("/{collaboratorId}/balance")
  public ResponseEntity<HourBankBalanceDto> handle(@PathVariable String collaboratorId) {
    var useCase = new CalculateHourBankBalanceUseCase(hourBankTransactionsRepository);
    var hourBankBalance = useCase.execute(collaboratorId);
    return ResponseEntity.ok(hourBankBalance);
  }
}
