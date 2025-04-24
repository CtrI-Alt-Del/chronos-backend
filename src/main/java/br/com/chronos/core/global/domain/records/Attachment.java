package br.com.chronos.core.global.domain.records;


import br.com.chronos.core.global.domain.dtos.AttachmentDto;

public record Attachment(Text name, Text key, Text contentType) {
  public static Attachment create(String name, String key, String contentType) {
    return new Attachment(
        Text.create(name, "attachment.name"),
        Text.create(key, "attachment.key"),
        Text.create(contentType, "attachment.contentType"));
  }

  public AttachmentDto getDto() {
    var dto = new AttachmentDto()
        .setName(name.value())
        .setKey(key.value())
        .setContentType(contentType.value());
    return dto;
  }
}
