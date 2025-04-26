package br.com.chronos.core.global.domain.records;

import br.com.chronos.core.global.domain.dtos.AttachmentDto;

public record Attachment(Text key, Text name, Text contentType) {
  public static Attachment create(String key, String name, String contentType) {
    return new Attachment(
        Text.create(key, "Chave do anexo"),
        Text.create(name, "Nome do anexo"),
        Text.create(contentType, "contentType do anexo"));
  }

  public AttachmentDto getDto() {
    var dto = new AttachmentDto()
        .setName(name.value())
        .setKey(key.value())
        .setContentType(contentType.value());
    return dto;
  }
}
