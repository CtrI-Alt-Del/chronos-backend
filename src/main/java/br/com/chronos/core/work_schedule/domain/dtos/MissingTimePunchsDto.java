package br.com.chronos.core.work_schedule.domain.dtos;

public class MissingTimePunchsDto {
  public int collaboratorsWithoutPunchs;

  public MissingTimePunchsDto setCollaboratorsWithoutPunchs(int clockOuts) {
    this.collaboratorsWithoutPunchs = clockOuts;
    return this;
  }
}
