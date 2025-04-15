package br.com.chronos.server.database.jpa.collaborator.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import kotlin.Pair;

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
import br.com.chronos.core.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.global.domain.records.Role.RoleName;
import br.com.chronos.server.database.jpa.collaborator.mappers.CollaboratorMapper;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;

interface JpaCollaboratorModelsRepository extends JpaRepository<CollaboratorModel, UUID> {
  public Optional<CollaboratorModel> findByAccountEmail(String email);

  public Optional<CollaboratorModel> findByCpf(String cpf);

  public List<CollaboratorModel> findAllByAccountIsActiveTrueAndAccountRoleNot(RoleName role);

  Page<CollaboratorModel> findAllByAccountRoleNotAndAccountIsActive(RoleName role, Pageable pageable, Boolean isActive);

  Page<CollaboratorModel> findAllByAccountRoleNotAndAccountSectorAndAccountIsActive(
      RoleName role,
      Sector sector,
      Boolean isActive,
      Pageable pageable);
}

public class JpaCollaboratorsRepository implements CollaboratorsRepository {
  @Autowired
  JpaCollaboratorModelsRepository repository;

  @Autowired
  CollaboratorMapper mapper;

  @Override
  public Array<Collaborator> findAllActive() {
    var collaboratorModels = repository.findAllByAccountIsActiveTrueAndAccountRoleNot(RoleName.ADMIN);
    return Array.createFrom(collaboratorModels, mapper::toEntity);
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
    collaboratorModels = repository.findAllByAccountRoleNotAndAccountIsActive(RoleName.ADMIN, pageRequest,
        isActive.value());
    var items = collaboratorModels.getContent().stream().toList();
    var itemsCount = collaboratorModels.getTotalElements();

    return new Pair<>(
        Array.createFrom(items, mapper::toEntity),
        PlusIntegerNumber.create((int) itemsCount, "contagem de colaboradores"));
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
  public void replace(Collaborator collaborator) {
    var collaboratorModel = mapper.toModel(collaborator);
    repository.save(collaboratorModel);
  }

  @Override
  public void remove(Collaborator collaborator) {
    var collaboratorModel = mapper.toModel(collaborator);
    repository.delete(collaboratorModel);
  }

  @Override
  public Pair<Array<Collaborator>, PlusIntegerNumber> findManyByCollaborationSector(CollaborationSector sector,
      Logical isActive, PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, 10);
    Page<CollaboratorModel> collaboratorModels;
    collaboratorModels = repository.findAllByAccountRoleNotAndAccountSectorAndAccountIsActive(
        RoleName.ADMIN,
        sector.value(),
        isActive.value(),
        pageRequest);
    var items = collaboratorModels.getContent().stream().toList();
    var itemsCount = collaboratorModels.getTotalElements();
    return new Pair<>(
        Array.createFrom(items, mapper::toEntity),
        PlusIntegerNumber.create((int) itemsCount, "contagem de colaboradores"));
  }
}
