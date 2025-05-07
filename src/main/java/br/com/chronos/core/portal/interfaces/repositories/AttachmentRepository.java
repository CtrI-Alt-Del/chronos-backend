package br.com.chronos.core.portal.interfaces.repositories;


import br.com.chronos.core.global.domain.records.Attachment;

public interface AttachmentRepository {

  void add(Attachment attachment );
  Attachment findAttachmentByKey(String attachmentKey);
  void remove(Attachment justificationType);
}
