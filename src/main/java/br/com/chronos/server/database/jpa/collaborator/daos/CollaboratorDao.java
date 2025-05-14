package br.com.chronos.server.database.jpa.collaborator.daos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.global.domain.records.Role.RoleName;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;

public interface CollaboratorDao extends JpaRepository<CollaboratorModel, UUID> {
  Optional<CollaboratorModel> findByAccountEmail(String email);

  Optional<CollaboratorModel> findByCpf(String cpf);

  List<CollaboratorModel> findAllByAccountSectorAndAccountRole(Sector sector, RoleName role);

  List<CollaboratorModel> findAllByAccountIsActiveTrueAndAccountRoleNot(RoleName role);

  Page<CollaboratorModel> findAllByAccountRoleNotAndAccountIsActive(RoleName role, Pageable pageable,
      Boolean isActive);

  Page<CollaboratorModel> findAllByAccountRoleNotAndAccountSectorAndAccountIsActiveAndIdNot(
      RoleName role,
      Sector sector,
      Boolean isActive,
      UUID id,
      Pageable pageable);
}
