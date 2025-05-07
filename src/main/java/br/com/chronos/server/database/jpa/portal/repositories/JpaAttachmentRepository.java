package br.com.chronos.server.database.jpa.portal.repositories;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.chronos.core.global.domain.records.Attachment;
import br.com.chronos.core.portal.interfaces.repositories.AttachmentRepository;
import br.com.chronos.server.database.jpa.portal.daos.AttachmentDao;
import br.com.chronos.server.database.jpa.portal.mappers.AttachmentMapper;

public class JpaAttachmentRepository implements AttachmentRepository {
  @Autowired
  private AttachmentMapper mapper;

  @Autowired
  private AttachmentDao dao;

  @Override
  public void add(Attachment attachment) {
    var model = mapper.toModel(attachment);
    dao.save(model);
  }

  @Override
  public void remove(Attachment attachment) {
    var model = mapper.toModel(attachment);
    dao.delete(model);
  }

  @Override
  public Attachment findAttachmentByKey(String attachmentKey) {
    var model = dao.findById(attachmentKey);
    if (model.isEmpty()) {
      return null;
    }
    return mapper.toRecord(model.get());
  }

}
