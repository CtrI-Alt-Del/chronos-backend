package br.com.chronos.core.work_schedule.domain.dtos;

public class WorkdayStatusReportDto {
  public Float activateCollaborators;
  public Float vacationCollaborators;
  public Float withdrawCollaborators;

  public WorkdayStatusReportDto setActivateCollaborators(Float activateCollaborators) {
    this.activateCollaborators = activateCollaborators;
    return this;
  }

  public WorkdayStatusReportDto setVacationCollaborators(Float vacationCollaborators) {
    this.vacationCollaborators = vacationCollaborators;
    return this;
  }

  public WorkdayStatusReportDto setWithdrawCollaborators(Float withdrawCollaborators) {
    this.withdrawCollaborators = withdrawCollaborators;
    return this;
  }

  public static WorkdayStatusReportDto createFromValues(Long activateCollaborators, Long vacationCollaborators,
      Long withdrawCollaborators) {
    long total = activateCollaborators + vacationCollaborators + withdrawCollaborators;
    float activatePercentage = (total == 0) ? 0f : ((float) activateCollaborators / total) * 100f;
    float vacationPercentage = (total == 0) ? 0f : ((float) vacationCollaborators / total) * 100f;
    float withdrawPercentage = (total == 0) ? 0f : ((float) withdrawCollaborators / total) * 100f;
    return new WorkdayStatusReportDto()
        .setActivateCollaborators(activatePercentage)
        .setVacationCollaborators(vacationPercentage)
        .setWithdrawCollaborators(withdrawPercentage);

  }
}
