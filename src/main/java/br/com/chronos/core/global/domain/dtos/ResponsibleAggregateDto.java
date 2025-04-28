package br.com.chronos.core.global.domain.dtos;

public class ResponsibleAggregateDto {
  public String id;
  public ResponsibleDto entity;

  public ResponsibleAggregateDto() {
  }

  public ResponsibleAggregateDto(ResponsibleDto responsibleDto) {
    this.id = responsibleDto.id;
    this.entity = responsibleDto;
  }

  public ResponsibleAggregateDto setId(String id) {
    this.id = id;
    return this;
  }

  public ResponsibleAggregateDto setResponsibleDto(ResponsibleDto responsibleDto) {
    this.entity = responsibleDto;
    return this;
  }
}