package br.com.chronos.server.database.jpa.collaborator.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.modules.global.domain.records.Cpf;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.PageNumber;
import br.com.chronos.core.modules.global.domain.records.PlusInteger;
import br.com.chronos.core.modules.global.domain.records.Role.RoleName;
import br.com.chronos.server.database.jpa.collaborator.mappers.CollaboratorMapper;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkScheduleModel;
import kotlin.Pair;

interface JpaCollaboratorModelsRepository extends JpaRepository<CollaboratorModel, UUID> {
  public Optional<CollaboratorModel> findByAccountEmail(String email);

  public Optional<CollaboratorModel> findByCpf(String cpf);

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
  public void update(Collaborator collaborator, Id workScheduleId) {
    var collaboratorModel = mapper.toModel(collaborator);
    collaboratorModel.setWorkSchedule(WorkScheduleModel.builder().id(workScheduleId.value()).build());
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
    collaboratorModel.setWorkSchedule(WorkScheduleModel.builder().id(collaborator.getWorkScheduleId().value()).build());
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
  public Pair<Array<Collaborator>, PlusInteger> findMany(PageNumber page, RoleName requesterRole,
      Sector requesterSector, Logical isActive) {
    var pageRequest = PageRequest.of(page.number().value() - 1, 20);
    Page<CollaboratorModel> collaboratorModels;
    if (requesterRole == RoleName.ADMIN) {
      collaboratorModels = repository.findAllByAccountRoleNotAndAccountIsActive(RoleName.ADMIN, pageRequest,
          isActive.value());
    } else {
      collaboratorModels = repository.findAllByAccountRoleNotAndAccountSectorAndAccountIsActive(RoleName.ADMIN,
          requesterSector,
          isActive.value(), pageRequest);
    }
    System.out.println(collaboratorModels);
    var items = collaboratorModels.getContent().stream().toList();
    var itemsCount = collaboratorModels.getTotalElements();

    return new Pair<>(
        Array.createFrom(items, mapper::toEntity),
        PlusInteger.create((int) itemsCount, "contagem de colaboradores"));
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
  public Id findWorkScheduleIdByCollaborator(Id collaboratorId) {
    var collaboratorModel = repository.findById(collaboratorId.value());
    var workScheduleUUID = collaboratorModel.get().getWorkSchedule().getId().toString();
    return Id.create(workScheduleUUID);
  }
}
