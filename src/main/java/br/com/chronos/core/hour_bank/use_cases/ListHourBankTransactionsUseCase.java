package br.com.chronos.core.hour_bank.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransactionOperation;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class ListHourBankTransactionsUseCase {
  private final HourBankTransactionsRepository repository;

  public ListHourBankTransactionsUseCase(HourBankTransactionsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<HourBankTransactionDto> execute(
      String collaboratorId,
      LocalDate startDate,
      LocalDate endDate,
      String operation,
      int page) {
    var response = repository.findManyByCollaborator(
        Id.create(collaboratorId),
        DateRange.create(startDate, endDate),
        HourBankTransactionOperation.create(operation),
        PageNumber.create(page));

    var dtos = response.getFirst().map((transaction) -> transaction.getDto()).list();
    var itemsCount = response.getSecond();
    return new PaginationResponse<HourBankTransactionDto>(dtos, itemsCount.value());
  }
}
