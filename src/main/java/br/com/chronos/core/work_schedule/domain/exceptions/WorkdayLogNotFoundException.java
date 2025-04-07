package br.com.chronos.core.work_schedule.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotFoundException;

public class WorkdayLogNotFoundException extends NotFoundException {
  public WorkdayLogNotFoundException() {
    super("Histórico de pontos não encontrado");
  }
}
