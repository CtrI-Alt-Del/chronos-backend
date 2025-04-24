package br.com.chronos.server.database.jpa.solicitation.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.solicitation.domain.dtos.JustificationDto;
import br.com.chronos.core.solicitation.domain.entities.Justification;
import br.com.chronos.server.database.jpa.solicitation.models.JustificationModel;
import br.com.chronos.server.database.jpa.solicitation.models.JustificationTypeModel;

@Component
public class JustificationMapper {

  @Autowired
  private JustificationTypeMapper justificationTypeMapper;

  @Autowired
  private AttachmentMapper attachmentMapper;

  public JustificationModel toModel(Justification entity) {
    var justificationTypeModel = JustificationTypeModel.builder()
        .id(entity.getJustificationType().getId().value())
        .build();
    var attachmentModel = entity.getAttachment() != null
        ? attachmentMapper.toModel(entity.getAttachment())
        : null;
    return JustificationModel.builder()
        .id(entity.getId().value())
        .description(entity.getDescription().value())
        .justificationType(justificationTypeModel)
        .attachment(attachmentModel)
        .build();
  }

  public JustificationDto toDto(JustificationModel model) {
    return new JustificationDto()
        .setId(model.getId().toString())
        .setDescription(model.getDescription())
        .setJustificationType(justificationTypeMapper.toDto(model.getJustificationType()))
        .setAttachment(model.getAttachment() != null
            ? attachmentMapper.toDto(model.getAttachment())
            : null);
  }

  public Justification toEntity(JustificationModel model) {
    return new Justification(toDto(model));
  }
}
