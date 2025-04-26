package br.com.chronos.server.api.controllers.solicitation.solicitations;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.solicitation.domain.dtos.DayOffSolicitationDto;
import br.com.chronos.core.solicitation.domain.dtos.JustificationDto;
import br.com.chronos.core.solicitation.domain.dtos.JustificationTypeDto;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffSolicitationRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.JustificationRepository;
import br.com.chronos.core.solicitation.use_cases.CreateDayOffSolicitationUseCase;
import br.com.chronos.server.api.aspects.annotations.global.HandleAttachmentUpload;
import br.com.chronos.server.api.aspects.contexts.global.AttachmentContextHolder;

@SolicitationsController
@HandleAttachmentUpload
public class CreateDayOffSolicitationController {

  @Autowired
  private DayOffSolicitationRepository solicitationsRepository;

  @Autowired
  private JustificationRepository justificationRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class Request extends DayOffSolicitationDto {
    private int workload;
  }

  // I suggests create a controller for creating a justification. This controller
  // will upload the justification's attchment too and be
  // executed after CreateDayOffSolicitationController.
  @PostMapping(value = "/day-off", consumes = { "multipart/form-data" })
  public ResponseEntity<DayOffSolicitationDto> handle(
      @RequestParam("dayOff") LocalDate dayOff,
      @RequestParam("description") String justificationDescription,
      @RequestParam("justificationTypeId") String justificationTypeId,
      @RequestParam("justificationTypeName") String justificationTypeName,
      @RequestParam("justificationTypeShouldHaveAttachment") String justificationTypeShouldHaveAttachment,
      @RequestParam(value = "attachment", required = false) MultipartFile attachment) {

    var attachmentDto = AttachmentContextHolder.get();

    var justificationDto = new JustificationDto()
        .setJustificationType(new JustificationTypeDto()
            .setId(justificationTypeId)
            .setName(justificationTypeName)
            .setShouldHaveAttachment(Boolean.parseBoolean(justificationTypeShouldHaveAttachment)))
        .setDescription(justificationDescription)
        .setAttachment(attachmentDto != null ? attachmentDto : null);

    var body = new DayOffSolicitationDto()
        .setDayOff(dayOff)
        .setJustification(justificationDto);

    var useCase = new CreateDayOffSolicitationUseCase(solicitationsRepository, justificationRepository);
    var responsible = authenticationProvider.getAccount();
    var senderId = responsible.getCollaboratorId();
    var response = useCase.execute(body, senderId);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
