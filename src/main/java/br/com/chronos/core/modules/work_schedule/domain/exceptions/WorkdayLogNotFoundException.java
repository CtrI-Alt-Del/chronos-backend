package br.com.chronos.core.modules.work_schedule.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.NotFoundException;

public class WorkdayLogNotFoundException extends NotFoundException {
  public WorkdayLogNotFoundException() {
    super("Histórico de pontos não encontrado");
  }
}
