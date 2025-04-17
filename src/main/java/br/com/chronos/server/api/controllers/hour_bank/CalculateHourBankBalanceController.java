package br.com.chronos.server.api.controllers.hour_bank;

import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.AllArgsConstructor;
import lombok.Data;

import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.CalculateHourBankBalanceUseCase;

@HourBankController
public class CalculateHourBankBalanceController {
  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  @Data
  @AllArgsConstructor
  private static class Response {
    private LocalTime balance;
  }

  @GetMapping("/{collaboratorId}/balance")
  public ResponseEntity<Response> handle(@PathVariable String collaboratorId) {
    var useCase = new CalculateHourBankBalanceUseCase(hourBankTransactionsRepository);
    var hourBankBalance = useCase.execute(collaboratorId);
    var response = new Response(hourBankBalance);
    return ResponseEntity.ok(response);
  }
}
