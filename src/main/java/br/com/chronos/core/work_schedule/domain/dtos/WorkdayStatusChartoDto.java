package br.com.chronos.core.work_schedule.domain.dtos;

public class WorkdayStatusChartoDto {
  public Float activateCollaborators;
  public Float vacationCollaborators;
  public Float withdrawCollaborators;

  public WorkdayStatusChartoDto setActivateCollaborators(Float activateCollaborators) {
    this.activateCollaborators = activateCollaborators;
    return this;
  }

  public WorkdayStatusChartoDto setVacationCollaborators(Float vacationCollaborators) {
    this.vacationCollaborators = vacationCollaborators;
    return this;
  }

  public WorkdayStatusChartoDto setWithdrawCollaborators(Float withdrawCollaborators) {
    this.withdrawCollaborators = withdrawCollaborators;
    return this;
  }

  public static WorkdayStatusChartoDto createFromValues(Long activateCollaborators, Long vacationCollaborators,
      Long withdrawCollaborators) {
    long total = activateCollaborators + vacationCollaborators + withdrawCollaborators;
    float activatePercentage = (total == 0) ? 0f : ((float) activateCollaborators / total) * 100f;
    float vacationPercentage = (total == 0) ? 0f : ((float) vacationCollaborators / total) * 100f;
    float withdrawPercentage = (total == 0) ? 0f : ((float) withdrawCollaborators / total) * 100f;
    return new WorkdayStatusChartoDto()
        .setActivateCollaborators(activatePercentage)
        .setVacationCollaborators(vacationPercentage)
        .setWithdrawCollaborators(withdrawPercentage);

  }
}
