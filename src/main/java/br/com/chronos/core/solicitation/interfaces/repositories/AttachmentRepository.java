package br.com.chronos.core.solicitation.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Attachment;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.entities.JustificationType;

public interface AttachmentRepository {

  void add(Attachment attachment );
  Attachment findAttachmentByKey(Id attachmentKey);
  void remove(Attachment justificationType);
}
