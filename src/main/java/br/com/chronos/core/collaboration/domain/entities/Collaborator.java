package br.com.chronos.core.collaboration.domain.entities;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.global.domain.abstracts.Entity;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Cpf;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Role;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.global.domain.records.TimeZone;
import br.com.chronos.core.work_schedule.domain.records.Workload;

public final class Collaborator extends Entity {
  private Text name;
  private Email email;
  private Cpf cpf;
  private Role role;
  private CollaborationSector sector;
  private Workload workload;
  private Logical isActive;
  private TimeZone timeZone;

  public Collaborator(CollaboratorDto dto) {
    super(dto.id);
    name = Text.create(dto.name, "nome do colaborador");
    email = Email.create(dto.email);
    cpf = Cpf.create(dto.cpf);
    role = Role.create(dto.role);
    sector = CollaborationSector.create(dto.sector);
    workload = Workload.create(dto.workload);
    isActive = (dto.isActive != null) ? Logical.create(dto.isActive) : Logical.create(true);
    timeZone = TimeZone.create(dto.timeZone);
  }

  public Logical hasSameSectorOf(Collaborator collaborator) {
    if (collaborator.getRole().isAdmin().isTrue()) {
      return Logical.createAsTrue();
    }

    return getSector().isEqual(collaborator.getSector());
  }

  public Text getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public Cpf getCpf() {
    return cpf;
  }

  public Workload getWorkload() {
    return workload;
  }

  public Role getRole() {
    return role;
  }

  public CollaborationSector getSector() {
    return sector;
  }

  public Logical getIsActive() {
    return isActive;
  }

  public TimeZone getTimeZone() {
    return timeZone;
  }

  public void update(CollaboratorDto dto) {
    if (dto.name != null && dto.name != this.getName().value()) {
      this.name = Text.create(dto.name, "Collaborator name");
    }
    if (dto.cpf != null && dto.cpf != this.getCpf().value()) {
      this.cpf = Cpf.create(dto.cpf);
    }
    if (dto.workload != 0 && dto.workload != this.getWorkload().value()) {
      this.workload = Workload.create(dto.workload);
    }
  }

  public CollaboratorDto getDto() {
    var dto = new CollaboratorDto()
        .setId(getId().value().toString())
        .setName(getName().value().toString())
        .setEmail(getEmail().value().toString())
        .setCpf(getCpf().value().toString())
        .setRole(getRole().value().toString())
        .setWorkload(getWorkload().value())
        .setSector(getSector().value().toString())
        .setActive(getIsActive().value())
        .setTimeZone(getTimeZone().value().toString());
    return dto;
  }
}
