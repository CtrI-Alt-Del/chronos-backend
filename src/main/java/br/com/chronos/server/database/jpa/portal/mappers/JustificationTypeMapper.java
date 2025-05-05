package br.com.chronos.server.database.jpa.portal.mappers;

import org.springframework.stereotype.Component;

import br.com.chronos.core.portal.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.portal.domain.entities.JustificationType;
import br.com.chronos.server.database.jpa.portal.models.JustificationTypeModel;

@Component
public class JustificationTypeMapper {
  public JustificationTypeModel toModel(JustificationType entity) {
    return JustificationTypeModel.builder()
        .id(entity.getId().value())
        .name(entity.getName().value())
        .shouldHaveAttachment(entity.getShouldHaveAttachment().value())
        .build();
  }

  public JustificationTypeDto toDto(JustificationTypeModel model) {
    var dto = new JustificationTypeDto()
        .setId(model.getId().toString())
        .setName(model.getName())
        .setShouldHaveAttachment(model.getShouldHaveAttachment());
    return dto;
  }

  public JustificationType toEntity(JustificationTypeModel model) {
    return new JustificationType(toDto(model));
  }
}
