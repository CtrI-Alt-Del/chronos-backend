package br.com.chronos.core.solicitation.domain.entities;

import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Attachment;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.solicitation.domain.dtos.JustificationDto;

public class Justification extends Entity {
  private JustificationType justificationType;
  private Attachment attachment;
  private Text description;

  public Justification(JustificationDto dto) {
    super(dto.id);
    description = Text.create(dto.description, "Justification description");
    justificationType = new JustificationType(dto.justificationType);
    attachment = dto.attachment != null ? Attachment.create(
        dto.attachment.name,
        dto.attachment.key,
        dto.attachment.contentType) : null;
    if (justificationType.NeedsAttachment().isTrue() && attachment == null) {
      throw new ValidationException("justificativa", "essa justificativa precisa de anexo!");

    }
  }

  public JustificationType getJustificationType() {
    return justificationType;
  }

  public Attachment getAttachment() {
    return attachment;
  }

  public Text getDescription() {
    return description;
  }

  public JustificationDto getDto() {
    var dto = new JustificationDto()
        .setId(getId().value().toString())
        .setJustificationType(justificationType.getDto())
        .setDescription(getDescription().value());
    if (attachment != null) {
      dto.setAttachment(attachment.getDto());
    }
    return dto;
  }
}
