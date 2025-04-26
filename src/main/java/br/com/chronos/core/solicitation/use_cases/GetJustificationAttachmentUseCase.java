package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.dtos.AttachmentDto;
import br.com.chronos.core.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.global.domain.records.Attachment;
import br.com.chronos.core.global.interfaces.providers.StorageProvider;
import br.com.chronos.core.solicitation.interfaces.repositories.AttachmentRepository;

public class GetJustificationAttachmentUseCase {
  private final StorageProvider storageProvider;

  public GetJustificationAttachmentUseCase(
       StorageProvider storageProvider) {
    this.storageProvider = storageProvider;
  }

  public String execute(String attachmentKey) {
    var attachmentUrl = storageProvider.getFileUrl(attachmentKey);
    return attachmentUrl;
  }
  // private Attachment findAttachment(String key){
  //   var attachment = attachmentRepository.findAttachmentByKey(key);
  //   if (attachment == null) {
  //     throw new NotFoundException("Attachment not found");
  //   }
  //   return attachment;
  // }
}
