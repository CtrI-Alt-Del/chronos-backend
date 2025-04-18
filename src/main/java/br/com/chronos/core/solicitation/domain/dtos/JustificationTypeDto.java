package br.com.chronos.core.solicitation.domain.dtos;
public class JustificationTypeDto {
  public String id;
  public String name;
  public Boolean shouldHaveAttachment;

  public JustificationTypeDto setId(String id) {
    this.id = id;
    return this;
  }

  public JustificationTypeDto setName(String name) {
    this.name = name;
    return this;
  }

  public JustificationTypeDto setShouldHaveAttachment(Boolean shouldHaveAttachment) {
    this.shouldHaveAttachment = shouldHaveAttachment;
    return this;
  }
}
