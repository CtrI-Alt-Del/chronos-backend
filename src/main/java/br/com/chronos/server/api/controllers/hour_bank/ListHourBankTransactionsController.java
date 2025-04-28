package br.com.chronos.server.api.controllers.hour_bank;

import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.ListHourBankTransactionsUseCase;

@HourBankController
public class ListHourBankTransactionsController {
  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  @GetMapping("/{collaboratorId}/transactions")
  public ResponseEntity<PaginationResponse<HourBankTransactionDto>> handle(
      @PathVariable String collaboratorId,
      @RequestParam(defaultValue = "") LocalDate startDate,
      @RequestParam(defaultValue = "") LocalDate endDate,
      @RequestParam(defaultValue = "all") String operation,
      @RequestParam(defaultValue = "1") int page) {
    var useCase = new ListHourBankTransactionsUseCase(hourBankTransactionsRepository);
    var response = useCase.execute(collaboratorId, startDate, endDate, operation, page);
    return ResponseEntity.ok(response);
  }
}
