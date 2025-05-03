package br.com.chronos.core.portal.domain.dtos;

import br.com.chronos.core.global.domain.dtos.AttachmentDto;

public class JustificationDto{
  public String id;
  public JustificationTypeDto justificationType;
  public AttachmentDto attachment;
  public String description;

  public JustificationDto setId(String id) {
    this.id = id;
    return this;
  }
  public JustificationDto setJustificationType(JustificationTypeDto justificationType) {
    this.justificationType = justificationType;
    return this;
  }
  public JustificationDto setAttachment(AttachmentDto attachment) {
    this.attachment = attachment;
    return this;
  }
  public JustificationDto setDescription(String description) {
    this.description = description;
    return this;
  }
}
