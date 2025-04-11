package br.com.chronos.server.database.jpa.collaborator.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Cpf;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.global.domain.records.Role;
import br.com.chronos.core.global.domain.records.Role.RoleName;
import br.com.chronos.server.database.jpa.collaborator.mappers.CollaboratorMapper;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import kotlin.Pair;

interface JpaCollaboratorModelsRepository extends JpaRepository<CollaboratorModel, UUID> {
  public Optional<CollaboratorModel> findByAccountEmail(String email);

  public Optional<CollaboratorModel> findByCpf(String cpf);

  public List<CollaboratorModel> findAllByAccountIsActiveTrue();

  Page<CollaboratorModel> findAllByAccountRoleNotAndAccountIsActive(
      Role.RoleName role,
      boolean isActive,
      Pageable pageable);

  Page<CollaboratorModel> findAllByAccountRoleNotAndAccountSectorAndAccountIsActive(
      Role.RoleName role,
      CollaborationSector.Sector sector,
      boolean isActive,
      Pageable pageable);
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
  public Optional<Collaborator> findById(Id id) {
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
  public void addMany(Array<Collaborator> collaborators) {
    var collaboratorModels = collaborators.map(mapper::toModel);
    repository.saveAll(collaboratorModels.list());
  }

  @Override
  public Pair<Array<Collaborator>, PlusIntegerNumber> findMany(Logical isActive, PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, 10);
    Page<CollaboratorModel> collaboratorModels;

    collaboratorModels = repository.findAllByAccountRoleNotAndAccountIsActive(
        RoleName.ADMIN,
        isActive.value(),
        pageRequest);

    var items = collaboratorModels.getContent().stream().toList();
    var itemsCount = collaboratorModels.getTotalElements();

    return new Pair<>(Array.createFrom(items, mapper::toEntity),
        PlusIntegerNumber.create((int) itemsCount, "contagem de colaboradores"));
  }

  @Override
  public Pair<Array<Collaborator>, PlusIntegerNumber> findManyByCollaborationSector(
      PageNumber page,
      CollaborationSector requesterSector,
      Logical isActive) {
    var pageRequest = PageRequest.of(page.number().value() - 1, 10);
    Page<CollaboratorModel> collaboratorModels;

    collaboratorModels = repository.findAllByAccountRoleNotAndAccountSectorAndAccountIsActive(RoleName.ADMIN,
        requesterSector.value(),
        isActive.value(),
        pageRequest);

    var items = collaboratorModels.getContent().stream().toList();
    var itemsCount = collaboratorModels.getTotalElements();

    return new Pair<>(Array.createFrom(items, mapper::toEntity),
        PlusIntegerNumber.create((int) itemsCount, "contagem de colaboradores"));
  }

  @Override
  public void delete(Collaborator collaborator) {
    var collaboratorModel = mapper.toModel(collaborator);
    repository.delete(collaboratorModel);
  }

  @Override
  public void disable(Collaborator collaborator) {
    CollaboratorModel collaboratorModel;
    collaboratorModel = repository.findById(collaborator.getId().value()).get();
    var accountModel = collaboratorModel.getAccount();
    accountModel.setIsActive(false);
    repository.save(collaboratorModel);
  }

  @Override
  public void enable(Collaborator collaborator) {
    CollaboratorModel collaboratorModel;
    collaboratorModel = repository.findById(collaborator.getId().value()).get();
    var accountModel = collaboratorModel.getAccount();
    accountModel.setIsActive(true);
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

  @Override
  public Array<Collaborator> findAllActive() {
    var collaboratorModels = repository.findAllByAccountIsActiveTrue();
    return Array.createFrom(collaboratorModels, mapper::toEntity);
  }
}
