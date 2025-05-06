package br.com.chronos.server.database.jpa.portal.daos;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.server.database.jpa.portal.models.DayOffScheduleAdjustmentSolicitationModel;

public interface DayOffScheduleAdjustmentSolicitationDao
    extends JpaRepository<DayOffScheduleAdjustmentSolicitationModel, UUID> {
  Page<DayOffScheduleAdjustmentSolicitationModel> findAllBySenderResponsibleAccountSectorOrderByDateDesc(
      Sector sector,
      Pageable pageable);
}
