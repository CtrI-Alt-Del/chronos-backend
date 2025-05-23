package br.com.chronos.server.database.jpa.portal.mappers;


import org.springframework.stereotype.Component;

import br.com.chronos.core.global.domain.dtos.AttachmentDto;
import br.com.chronos.core.global.domain.records.Attachment;
import br.com.chronos.server.database.jpa.portal.models.AttachmentModel;

@Component
public class AttachmentMapper {
  public AttachmentModel toModel(Attachment record) {
    return AttachmentModel.builder()
        .name(record.name().value())
        .contentType(record.contentType().value())
        .id(record.key().value())
        .build();
  }

  public Attachment toRecord(AttachmentModel model) {
    var record = Attachment.create(
        model.getId().toString(),
        model.getName(),
        model.getContentType());
    return record;
  }

  public AttachmentDto toDto(AttachmentModel model) {
    return new AttachmentDto()
        .setName(model.getName())
        .setContentType(model.getContentType())
        .setKey(model.getId().toString());
  }
}
