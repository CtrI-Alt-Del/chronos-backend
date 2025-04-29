package br.com.chronos.core.work_schedule.use_cases;

import java.io.IOException;

import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.records.TimeCard;
import br.com.chronos.core.work_schedule.interfaces.PdfProvider;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class GenerateTimeCardPdfUseCase {
  private final WorkdayLogsRepository repository;
  private final PdfProvider pdfProvider;

  public GenerateTimeCardPdfUseCase(WorkdayLogsRepository repository, PdfProvider pdfProvider) {
    this.repository = repository;
    this.pdfProvider = pdfProvider;
  }

  public void execute(String collaboratorId, int month, int year) {
    var dateRange = DateRange.createAsMonthRange(month, year);
    var workdayLogs = repository.findAllByCollaboratorAndDateRange(
        Id.create(collaboratorId),
        dateRange);
    var timeCard = TimeCard.create(workdayLogs);
    var collaboratorResponsible = workdayLogs.firstItem().getResponsible().getEntity();

    try {
      pdfProvider.generateTimeCardPdf(timeCard, dateRange, collaboratorResponsible);
    } catch (IOException e) {
      System.out.println("Error generating PDF: " + e.getMessage());
    }

  }
}
