package br.com.chronos.core.modules.collaboration.use_cases;

import br.com.chronos.core.modules.auth.domain.exceptions.NotAuthorizedException;
import br.com.chronos.core.modules.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.domain.exceptions.CollaboratorNotFoundException;
import br.com.chronos.core.modules.collaboration.domain.exceptions.ExistingCpfException;
import br.com.chronos.core.modules.collaboration.domain.exceptions.ExistingEmailException;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector;
import br.com.chronos.core.modules.global.domain.records.Cpf;
import br.com.chronos.core.modules.global.domain.records.Email;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Role;

public class UpdateCollaboratorUseCase {
  private final CollaboratorsRepository repository;

  public UpdateCollaboratorUseCase(CollaboratorsRepository repository) {
    this.repository = repository;
  }

  public CollaboratorDto execute(String collaboratorId, CollaboratorDto dto, Id workScheduleId,
      CollaborationSector responsibleSector, Role responsibleRole) {
    if (dto == null) {
      dto = new CollaboratorDto();
    }
    var collaborator = findCollaborator(Id.create(collaboratorId));

    if (collaborator.isFromSameSector(responsibleSector, responsibleRole).isFalse()) {
      throw new NotAuthorizedException();
    }
    if (dto.sector != null && responsibleRole.isAdmin().isFalse()) {
      throw new NotAuthorizedException();
    }
    validateUniqueEmailAndCpf(dto, Id.create(collaboratorId));
    collaborator.update(dto);

    if (workScheduleId != null) {
      repository.update(collaborator, workScheduleId);
    } else {
      var workId = getWorkScheduleId(collaborator.getId());
      repository.update(collaborator, workId);
    }

    return collaborator.getDto();
  }

  private Collaborator findCollaborator(Id collaboratorId) {
    var collaborator = repository.findCollaboratorById(collaboratorId);
    if (collaborator.isEmpty()) {
      throw new CollaboratorNotFoundException();

    }
    return collaborator.get();
  }

  private void validateUniqueEmailAndCpf(CollaboratorDto dto, Id currentId) {
    if (dto.email == null && dto.cpf == null)
      return;

    var existingCollaborator = repository.findByEmailOrCpf(dto.email, dto.cpf);
    Email email = dto.email != null ? Email.create(dto.email) : null;
    Cpf cpf = dto.cpf != null ? Cpf.create(dto.cpf) : null;

    if (existingCollaborator.isEmpty() || existingCollaborator.get().getId().equals(currentId)) {
      return;
    }

    if (email != null && existingCollaborator.get().getEmail().equals(email)) {
      throw new ExistingEmailException();
    }

    if (cpf != null && existingCollaborator.get().getCpf().equals(cpf)) {
      throw new ExistingCpfException();
    }
  }

  private Id getWorkScheduleId(Id collaboratorId) {
    var workScheduleId = repository.findWorkScheduleIdByCollaborator(collaboratorId);
    return workScheduleId;
  }
}
