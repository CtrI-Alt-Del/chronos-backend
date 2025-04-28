package br.com.chronos.server.database.jpa.solicitation.daos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.server.database.jpa.solicitation.models.SolicitationModel;

public interface SolicitationDao extends JpaRepository<SolicitationModel, UUID> {
  List<SolicitationModel> findAllBySenderResponsibleAccountSector(CollaborationSector.Sector sector);

  Optional<SolicitationModel> findById(UUID id);

  List<SolicitationModel> findAllBySenderResponsibleId(UUID userId);
}
