package br.com.chronos.server.database.jpa.solicitation.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.Attachment;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.interfaces.repositories.AttachmentRepository;
import br.com.chronos.server.database.jpa.solicitation.mappers.AttachmentMapper;
import br.com.chronos.server.database.jpa.solicitation.models.AttachmentModel;

interface JpaAttachmentModelRepository extends JpaRepository<AttachmentModel, String> {

}

public class JpaAttachmentRepository implements AttachmentRepository {

  @Autowired
  private AttachmentMapper mapper;

  @Autowired
  private JpaAttachmentModelRepository repository;

  @Override
  public void add(Attachment attachment) {
    var model = mapper.toModel(attachment);
    repository.save(model);
  }

  @Override
  public void remove(Attachment attachment) {
    var model = mapper.toModel(attachment);
    repository.delete(model);
  }

	@Override
	public Attachment findAttachmentByKey(String attachmentKey) {
    var model = repository.findById(attachmentKey);
    if(model.isEmpty()){
      return null;
    }
    return mapper.toRecord(model.get());
	}

}
