package br.com.chronos.core.solicitation.use_cases;

import br.com.chronos.core.global.domain.dtos.AttachmentDto;
import br.com.chronos.core.global.domain.records.Attachment;
import br.com.chronos.core.global.interfaces.providers.StorageProvider;
import br.com.chronos.core.solicitation.interfaces.repositories.AttachmentRepository;

public class UploadJustificationAttachmentUseCase {
  private final StorageProvider storageProvider;
  private final AttachmentRepository attachmentRepository;

  public UploadJustificationAttachmentUseCase(StorageProvider storageProvider,
      AttachmentRepository attachmentRepository) {
    this.storageProvider = storageProvider;
    this.attachmentRepository = attachmentRepository;
  }
  public AttachmentDto execute(String attachmentName,String attachmentType,byte[] fileContent){
    var attachmentKey = storageProvider.uploadFile(attachmentName, attachmentType, fileContent);
    var attachment = Attachment.create(attachmentKey, attachmentName, attachmentType);
    attachmentRepository.add(attachment);
    return attachment.getDto();
  }
}
