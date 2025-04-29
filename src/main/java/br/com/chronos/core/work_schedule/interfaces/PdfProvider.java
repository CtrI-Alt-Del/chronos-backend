package br.com.chronos.core.work_schedule.interfaces;

import java.io.IOException;

import br.com.chronos.core.global.domain.entities.Responsible;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.work_schedule.domain.records.TimeCard;

public interface PdfProvider {
  void generateTimeCardPdf(
      TimeCard timeCard,
      DateRange dateRange,
      Responsible responsible) throws IOException;
}