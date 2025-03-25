package br.com.chronos.core.modules.global.domain.dtos;

public class ResponsibleAggregateDto {
  public String id;
  public ResponsibleDto responsibleDto;

  public ResponsibleAggregateDto() {
  }

  public ResponsibleAggregateDto(ResponsibleDto responsibleDto) {
    this.id = responsibleDto.id;
    this.responsibleDto = responsibleDto;
  }

  public ResponsibleAggregateDto setId(String id) {
    this.id = id;
    return this;
  }

  public ResponsibleAggregateDto setResponsibleDto(ResponsibleDto responsibleDto) {
    this.responsibleDto = responsibleDto;
    return this;
  }
}