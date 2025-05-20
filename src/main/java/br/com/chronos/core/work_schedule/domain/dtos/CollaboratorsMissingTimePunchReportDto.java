package br.com.chronos.core.work_schedule.domain.dtos;

import java.util.List;

public class CollaboratorsMissingTimePunchReportDto {
  public List<MissingTimePunchsDto> collaboratorsWithoutPunchs;

  public CollaboratorsMissingTimePunchReportDto setCollaboratorsMissingTimePunchReportDto(
      List<MissingTimePunchsDto> clockEvents) {
    this.collaboratorsWithoutPunchs = clockEvents;
    return this;
  }

  public static CollaboratorsMissingTimePunchReportDto createFromValues(List<Integer> clockEvents) {
    var clockEventsDto = clockEvents.stream()
        .map(clockEvent -> new MissingTimePunchsDto()
            .setCollaboratorsWithoutPunchs(clockEvent))
        .toList();
    return new CollaboratorsMissingTimePunchReportDto()
        .setCollaboratorsMissingTimePunchReportDto(clockEventsDto);
  }
}
