package br.com.chronos.server.api.controllers.portal.justification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import br.com.chronos.core.portal.domain.dtos.ExcusedAbsenceSolicitationDto;
import br.com.chronos.core.portal.domain.dtos.JustificationDto;
import br.com.chronos.core.portal.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.portal.interfaces.repositories.JustificationRepository;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.use_cases.AttachJustificationToSolicitationUseCase;
import br.com.chronos.server.api.aspects.annotations.global.HandleAttachmentUpload;
import br.com.chronos.server.api.aspects.contexts.global.AttachmentContextHolder;

@JustificationController
@HandleAttachmentUpload
public class AttachJustificationToSolicitationController {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private JustificationRepository justificationRepository;

  @PostMapping(value = "/attach", consumes = "multipart/form-data")
  public ResponseEntity<ExcusedAbsenceSolicitationDto> handle(
      @RequestPart("solicitationId") String solicitationId,
      @RequestPart("justificationTypeId") String justificationTypeId,
      @RequestPart("justificationTypeName") String justificationTypeName,
      @RequestPart("justificationTypeShouldHaveAttachment") String justificationTypeShouldHaveAttachment,
      @RequestPart("description") String description,
      @RequestPart(value = "attachment",required = false) MultipartFile attachment) {
    var attachmentDto = AttachmentContextHolder.get();
    var justificationDto = new JustificationDto()
        .setJustificationType(new JustificationTypeDto()
            .setId(justificationTypeId)
            .setName(justificationTypeName)
            .setShouldHaveAttachment(Boolean.parseBoolean(justificationTypeShouldHaveAttachment)))
        .setDescription(description)
        .setAttachment(attachmentDto != null ? attachmentDto : null);
    var useCase = new
    AttachJustificationToSolicitationUseCase(solicitationsRepository,justificationRepository);
    var response = useCase.execute(solicitationId, justificationDto);
    return ResponseEntity.ok(response);
  }

}
