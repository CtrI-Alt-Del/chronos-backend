package br.com.chronos.core.global.domain.dtos;
public class AttachmentDto {
  public String name;
  public String key;
  public String contentType;

  public AttachmentDto setName(String name) {
    this.name = name;
    return this;
  }

  public AttachmentDto setKey(String key) {
    this.key = key;
    return this;
  }


  public AttachmentDto setContentType(String contentType) {
    this.contentType = contentType;
    return this;
  }
}
