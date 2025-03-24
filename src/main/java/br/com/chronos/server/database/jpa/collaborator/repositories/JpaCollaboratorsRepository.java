package br.com.chronos.server.database.jpa.collaborator.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Cpf;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Page;
import br.com.chronos.core.modules.global.domain.records.PlusInteger;
import br.com.chronos.core.modules.global.responses.PaginationResponse;
import br.com.chronos.server.database.jpa.collaborator.mappers.CollaboratorMapper;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkScheduleModel;
import kotlin.Pair;

interface JpaCollaboratorModelsRepository extends JpaRepository<CollaboratorModel, UUID> {
  public Optional<CollaboratorModel> findByAccountEmail(String email);

  public Optional<CollaboratorModel> findByCpf(String cpf);
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
  public void add(Collaborator collaborator, Id workScheduleId) {
    var collaboratorModel = mapper.toModel(collaborator);
    collaboratorModel.setWorkSchedule(WorkScheduleModel.builder().id(workScheduleId.value()).build());
    repository.save(collaboratorModel);
  }

  @Override
  public void addMany(Array<Collaborator> collaborators, Id workScheduleId) {
    var collaboratorModels = collaborators.map((collaborator) -> {
      var collaboratorModel = mapper.toModel(collaborator);
      collaboratorModel.setWorkSchedule(WorkScheduleModel.builder().id(workScheduleId.value()).build());
      return collaboratorModel;
    });
    repository.saveAll(collaboratorModels.list());
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

  @Override
  public Optional<Collaborator> findByEmail(Email email) {
    var collaboratorModel = repository.findByAccountEmail(email.value());
    if (collaboratorModel.isEmpty()) {
      return Optional.empty();
    }
    var collaborator = mapper.toEntity(collaboratorModel.get());
    return Optional.of(collaborator);
  }

  @Override
  public Optional<Collaborator> findByCpf(Cpf cpf) {
    var collaboratorModel = repository.findByCpf(cpf.value());
    if (collaboratorModel.isEmpty()) {
      return Optional.empty();
    }
    var collaborator = mapper.toEntity(collaboratorModel.get());
    return Optional.of(collaborator);
  }

  @Override
  public Optional<Collaborator> findByEmailOrCpf(String email, String cpf) {
    var collaboratorModel = repository.findByAccountEmail(email);
    if (collaboratorModel.isPresent()) {
      return Optional.of(mapper.toEntity(collaboratorModel.get()));
    }

    collaboratorModel = repository.findByCpf(cpf);
    return collaboratorModel.map(mapper::toEntity);
  }
}
