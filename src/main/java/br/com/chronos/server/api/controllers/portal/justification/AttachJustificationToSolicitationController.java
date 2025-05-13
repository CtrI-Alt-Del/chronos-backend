package br.com.chronos.server.api.controllers.portal.justification;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import br.com.chronos.core.global.domain.dtos.AttachmentDto;
import br.com.chronos.core.global.interfaces.providers.StorageProvider;
import br.com.chronos.core.portal.domain.dtos.ExcusedAbsenceSolicitationDto;
import br.com.chronos.core.portal.domain.dtos.JustificationDto;
import br.com.chronos.core.portal.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.portal.interfaces.repositories.AttachmentRepository;
import br.com.chronos.core.portal.interfaces.repositories.JustificationRepository;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.portal.use_cases.AttachJustificationToSolicitationUseCase;
import br.com.chronos.core.portal.use_cases.UploadJustificationAttachmentUseCase;

@JustificationController
public class AttachJustificationToSolicitationController {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private JustificationRepository justificationRepository;

  @Autowired
  private AttachmentRepository attachmentRepository;

  @Autowired
  private StorageProvider storageProvider;

  @PostMapping(value = "/attach", consumes = "multipart/form-data")
  public ResponseEntity<ExcusedAbsenceSolicitationDto> handle(
      @RequestPart("solicitationId") String solicitationId,
      @RequestPart("justificationTypeId") String justificationTypeId,
      @RequestPart("justificationTypeName") String justificationTypeName,
      @RequestPart("justificationTypeShouldHaveAttachment") String justificationTypeShouldHaveAttachment,
      @RequestPart("description") String description,
      @RequestPart(value = "attachment", required = false) MultipartFile attachment) throws IOException {
    var justificationDto = new JustificationDto()
        .setJustificationType(new JustificationTypeDto()
            .setId(justificationTypeId)
            .setName(justificationTypeName)
            .setShouldHaveAttachment(Boolean.parseBoolean(justificationTypeShouldHaveAttachment)))
        .setDescription(description);

    AttachmentDto attachmentDto = null;

    if (attachment != null && !attachment.isEmpty()) {
      var attachmentUploadUseCase = new UploadJustificationAttachmentUseCase(storageProvider, attachmentRepository);
      attachmentDto = attachmentUploadUseCase.execute(
          attachment.getOriginalFilename(),
          attachment.getContentType(),
          attachment.getBytes());
    }

    var attachJustificationToSolicitationUseCase = new AttachJustificationToSolicitationUseCase(solicitationsRepository,
        justificationRepository);

    var response = attachJustificationToSolicitationUseCase.execute(solicitationId, justificationDto, attachmentDto);

    return ResponseEntity.ok(response);
  }

}
