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
    name = Text.create(dto.name, "Jusitification type name");
    shouldHaveAttachment = Logical.create(dto.shouldHaveAttachment);
  }
  public Text getName() {
    return name;
  }
  public Logical getShouldHaveAttachment() {
    return shouldHaveAttachment;
  }
  public Logical NeedsAttachment() {
    return Logical.create(shouldHaveAttachment.isTrue());
  }
  public JustificationTypeDto getDto(){
    var dto = new JustificationTypeDto()
      .setId(getId().value().toString())
      .setName(getName().value())
      .setShouldHaveAttachment(getShouldHaveAttachment().value());
    return dto;
  }
}
