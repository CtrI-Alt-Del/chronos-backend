package br.com.chronos.server.api.controllers.hour_bank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.CreateHourBankTransactionForPaidOvertimeUseCase;

@HourBankController
public class CreateHourBankTransactionForPaidOvertimeController {
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  @PostMapping("/paid-overtime")
  public ResponseEntity<Void> handle() {
    var account = authenticationProvider.getAccount();
    var collaboratorId = account.getCollaboratorId().toString();
    var useCase = new CreateHourBankTransactionForPaidOvertimeUseCase(hourBankTransactionsRepository);
    useCase.execute(collaboratorId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}