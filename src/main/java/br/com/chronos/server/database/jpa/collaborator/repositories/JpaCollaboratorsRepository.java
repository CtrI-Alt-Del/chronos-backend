package br.com.chronos.server.database.jpa.collaborator.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Page;
import br.com.chronos.core.modules.global.domain.records.PlusInteger;
import br.com.chronos.core.modules.global.responses.PaginationResponse;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.server.database.jpa.collaborator.mappers.CollaboratorMapper;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import kotlin.Pair;

interface JpaCollaboratorModelsRepository extends JpaRepository<CollaboratorModel, UUID> {

}

public class JpaCollaboratorsRepository implements CollaboratorsRepository {
  @Autowired
  JpaCollaboratorModelsRepository repository;

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
  public Pair<Array<Collaborator>, PlusInteger> findMany(Page page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var collaboratorModels = repository.findAll(pageRequest);
    var items = collaboratorModels.getContent().stream().toList();
    var itemsCount = collaboratorModels.getTotalElements();

    return new Pair<>(
        Array.createFrom(items, mapper::toEntity),
        PlusInteger.create((int) itemsCount, "contagem de colaboradores"));
  }

  @Override
  public void disable(Collaborator collaborator) {
    var collaboratorModel = mapper.toModel(collaborator);
    repository.save(collaboratorModel);
  }

  @Override
  public void enable(Collaborator collaborator) {
    var collaboratorModel = mapper.toModel(collaborator);
    repository.save(collaboratorModel);
  }

}
