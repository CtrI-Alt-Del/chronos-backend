package br.com.chronos.server.database.jpa.collaborator.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.server.database.jpa.collaborator.mappers.CollaboratorMapper;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import kotlin.Pair;

interface JpaCollaboratorModelRepository extends JpaRepository<CollaboratorModel, UUID> {
}

public class JpaCollaboratorRepository implements CollaboratorsRepository {
  @Autowired
  JpaCollaboratorModelRepository repository;

  @Autowired
  CollaboratorMapper mapper;

  @Override
  public void update(Collaborator collaborator) {
    var collaboratorModel = mapper.toModel(collaborator);
    repository.save(collaboratorModel);
  }

  @Override
  public Optional<Collaborator> findCollaboratorById(Id id) {
    var collaboratorModel = repository.findById(id.value());
    if (collaboratorModel.isEmpty()) {
      return Optional.empty();
    }
    var collaborator = mapper.toEntity(collaboratorModel.get());
    return Optional.of(collaborator);
  }

  @Override
  public void add(Collaborator collaborator) {
    var collaboratorModel = mapper.toModel(collaborator);
    repository.save(collaboratorModel);
  }

  @Override
  public void delete(Collaborator collaborator) {
    var collaboratorModel = mapper.toModel(collaborator);
    repository.delete(collaboratorModel);
  }

  @Override
  public Pair<Array<Collaborator>, Long> findMany(int page, int itemsPerPage) {
    var pageable = PageRequest.of(page - 1, itemsPerPage);
    var collaboratorModels = repository.findAll(pageable);
    var items = collaboratorModels.getContent().stream().toList();
    var itemsCount = collaboratorModels.getTotalElements();
    return new Pair<>(Array.createFrom(items, mapper::toEntity), itemsCount);
  }
}
