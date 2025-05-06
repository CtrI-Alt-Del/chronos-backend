package br.com.chronos.server.database.jpa.portal.daos;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.server.database.jpa.portal.models.TimePunchAdjustmentSolicitationModel;

public interface TimePunchAdjustmentSolicitationDao extends JpaRepository<TimePunchAdjustmentSolicitationModel, UUID> {
  Page<TimePunchAdjustmentSolicitationModel> findAllBySenderResponsibleAccountSectorOrderByDateDesc(
      CollaborationSector.Sector sector,
      PageRequest pageRequest);
}
