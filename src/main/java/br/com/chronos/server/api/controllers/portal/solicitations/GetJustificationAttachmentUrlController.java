package br.com.chronos.server.api.controllers.portal.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chronos.core.global.interfaces.providers.StorageProvider;
import br.com.chronos.core.portal.use_cases.GetJustificationAttachmentUseCase;
import lombok.Data;

@SolicitationsController
public class GetJustificationAttachmentUrlController {

  @Autowired
  private StorageProvider storageProvider;

  @Data
  private static class Response {
    private String url;
  }

  @GetMapping("/attachments/{attachmentKey}")
  public ResponseEntity<Response> handle(@PathVariable String attachmentKey){
    var useCase = new GetJustificationAttachmentUseCase(storageProvider);
    var attachmentUrl = useCase.execute(attachmentKey);
    Response response = new Response();
    response.setUrl(attachmentUrl);
    return ResponseEntity.ok().body(response);
    } 
}
