package br.com.chronos.server.database.jpa.portal.daos;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.server.database.jpa.portal.models.VacationSolicitationModel;

public interface VacationSolicitationDao extends JpaRepository<VacationSolicitationModel, UUID> {
  Page<VacationSolicitationModel> findAllBySenderResponsibleAccountSectorOrderByDateDesc(
      CollaborationSector.Sector sector,
      PageRequest pageRequest);
}
