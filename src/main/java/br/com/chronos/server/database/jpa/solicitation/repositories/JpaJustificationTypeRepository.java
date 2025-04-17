package br.com.chronos.server.database.jpa.solicitation.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.solicitation.domain.entities.JustificationType;
import br.com.chronos.core.solicitation.interfaces.repositories.JustificationTypeRepository;
import br.com.chronos.server.database.jpa.solicitation.mappers.JustificationTypeMapper;
import br.com.chronos.server.database.jpa.solicitation.models.JustificationTypeModel;

interface JpaJustificationTypeModelsRepository extends JpaRepository<JustificationTypeModel,UUID>{
  Optional<JustificationTypeModel> findById(Id id);
}

public class JpaJustificationTypeRepository implements JustificationTypeRepository{

  @Autowired
  private JpaJustificationTypeModelsRepository repository;

  @Autowired
  private JustificationTypeMapper mapper;

	@Override
	public void add(JustificationType justificationType) {
    var justificationTypeModel = mapper.toModel(justificationType);
    repository.save(justificationTypeModel);
	}

	@Override
	public void remove(JustificationType justificationType) {
    var justificationTypeModel = mapper.toModel(justificationType);
    repository.delete(justificationTypeModel);
	}

	@Override
	public Optional<JustificationType> findById(Id id) {
    var justificationTypeModel = repository.findById(id.value());
    if (justificationTypeModel.isEmpty()) {
      return Optional.empty();
    }
    var justificationType = mapper.toEntity(justificationTypeModel.get());
    return Optional.of(justificationType);
	}

	@Override
	public void replace(JustificationType justificationType) {
    var justificationTypeModel = mapper.toModel(justificationType);
    repository.save(justificationTypeModel);
	}

	@Override
	public Array<JustificationType> findAll() {
    var justificationTypeModels = repository.findAll();
    return Array.createFrom(justificationTypeModels, mapper::toEntity);
	}
}
