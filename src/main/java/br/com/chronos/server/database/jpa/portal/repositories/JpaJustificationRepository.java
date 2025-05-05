package br.com.chronos.server.database.jpa.portal.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.Justification;
import br.com.chronos.core.portal.interfaces.repositories.JustificationRepository;
import br.com.chronos.server.database.jpa.portal.mappers.JustificationMapper;
import br.com.chronos.server.database.jpa.portal.models.JustificationModel;

interface JpaJustificationModelRepository extends JpaRepository<JustificationModel,UUID>{}

public class JpaJustificationRepository implements JustificationRepository{

  @Autowired
  private JpaJustificationModelRepository repository;

  @Autowired
  private JustificationMapper mapper;

	@Override
	public void add(Justification justification) {
    var justificationModel = mapper.toModel(justification);
    repository.save(justificationModel);
	}

	@Override
	public void remove(Justification justification) {
    var justificationModel = mapper.toModel(justification);
    repository.delete(justificationModel);
	}

	@Override
	public Optional<Justification> findById(Id id) {
    var justificationModel = repository.findById(id.value());
    if (justificationModel.isEmpty()) {
      return Optional.empty();
    }
    var justification = mapper.toEntity(justificationModel.get());
    return Optional.of(justification);
	}

	@Override
	public Array<Justification> findAll() {
    var justificationModels = repository.findAll();
    var justifications = Array.createFrom(justificationModels, mapper::toEntity);
    return justifications;
	}

}
