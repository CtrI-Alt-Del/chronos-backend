package br.com.chronos.server.database.jpa.portal.daos;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.portal.domain.records.SolicitationStatus;
import br.com.chronos.server.database.jpa.portal.models.WorkLeaveSolicitationModel;

public interface WorkLeaveSolicitationDao extends JpaRepository<WorkLeaveSolicitationModel, UUID> {
  Page<WorkLeaveSolicitationModel> findAllBySenderResponsibleAccountSectorAndIsVacationOrderByDateDesc(
      CollaborationSector.Sector sector,
      Boolean isVacation,
      PageRequest pageRequest);

  @Query(value = """
      SELECT wls FROM WorkLeaveSolicitationModel wls
      WHERE wls.solicitationStatus = :status
      AND wls.senderResponsible.account.sector = :sector
      AND wls.startedAt <= :endedAt
      AND wls.endedAt >= :startedAt
      ORDER BY wls.startedAt DESC
      """)
  Page<WorkLeaveSolicitationModel> findAllBySolicitationStatusAndCollaborationSectorAndDateRange(
      SolicitationStatus.Status status,
      CollaborationSector.Sector sector,
      LocalDate startedAt,
      LocalDate endedAt,
      PageRequest pageRequest);
}
