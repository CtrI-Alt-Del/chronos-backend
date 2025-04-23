package br.com.chronos.core.solicitation.domain.entities;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.solicitation.domain.dtos.JustificationTypeDto;

public class JustificationType extends Entity {
  private Text name;
  private Logical shouldHaveAttachment;

  public JustificationType(JustificationTypeDto dto) {
    super(dto.id);
    name = dto.name != null ? Text.create(dto.name, "Jusitification type name") : null;
    shouldHaveAttachment = dto.shouldHaveAttachment != null ? Logical.create(dto.shouldHaveAttachment) : null;
  }

  public Text getName() {
    return name;
  }

  public Logical getShouldHaveAttachment() {
    return shouldHaveAttachment;
  }

  public JustificationType updateName(Text name) {
    this.name = name;
    return this;
  }

  public JustificationType updateShouldHaveAttachment(Logical shouldHaveAttachment) {
    this.shouldHaveAttachment = shouldHaveAttachment;
    return this;
  }

  public Logical NeedsAttachment() {
    return Logical.create(shouldHaveAttachment != null && shouldHaveAttachment.isTrue());
  }

  public JustificationTypeDto getDto() {
    var dto = new JustificationTypeDto()
        .setId(getId().value().toString())
        .setName(getName() != null ? getName().value() : null)
        .setShouldHaveAttachment(getShouldHaveAttachment() != null ? getShouldHaveAttachment().value() : null);
    return dto;
  }
}
