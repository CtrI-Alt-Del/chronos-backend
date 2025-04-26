package br.com.chronos.core.hour_bank.use_cases;

import java.time.LocalDate;
import kotlin.Pair;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
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
    if (operation.equals("all") || operation == null) {
      var response = repository.findManyByCollaboratorAndDateRage(
          Id.create(collaboratorId),
          DateRange.create(startDate, endDate),
          PageNumber.create(page));

      return getPaginationResponse(response);
    }

    System.out.println(startDate);
    System.out.println(endDate);

    var response = repository.findManyByCollaboratorDateRageAndOperation(
        Id.create(collaboratorId),
        DateRange.create(startDate, endDate),
        HourBankTransactionOperation.create(operation),
        PageNumber.create(page));

    return getPaginationResponse(response);
  }

  private PaginationResponse<HourBankTransactionDto> getPaginationResponse(
      Pair<Array<HourBankTransaction>, PlusIntegerNumber> data) {
    var dtos = data.getFirst().map((transaction) -> transaction.getDto()).list();
    var itemsCount = data.getSecond();
    return new PaginationResponse<HourBankTransactionDto>(dtos, itemsCount.value());
  }
}
