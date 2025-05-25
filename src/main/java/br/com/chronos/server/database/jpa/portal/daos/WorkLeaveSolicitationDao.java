package br.com.chronos.server.database.jpa.portal.daos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.portal.domain.records.SolicitationStatus;
import br.com.chronos.server.database.jpa.portal.models.WorkLeaveSolicitationModel;

public interface WorkLeaveSolicitationDao extends JpaRepository<WorkLeaveSolicitationModel, UUID> {
  Page<WorkLeaveSolicitationModel> findAllBySenderResponsibleAccountSectorAndIsVacationOrderByDateDesc(
      CollaborationSector.Sector sector,
      Boolean isVacation,
      PageRequest pageRequest);

  Page<WorkLeaveSolicitationModel> findAllBySenderResponsibleAccountSectorAndSenderResponsibleNameContainingIgnoreCase(
      CollaborationSector.Sector sector,
      String senderName,
      PageRequest pageRequest);

  @Query(value = """
      SELECT wls FROM WorkLeaveSolicitationModel wls
      WHERE wls.solicitationStatus = :status
      AND wls.senderResponsible.id = :senderId
      AND wls.startedAt <= :endedAt
      AND wls.endedAt >= :startedAt
      ORDER BY wls.startedAt DESC
      """)
  List<WorkLeaveSolicitationModel> findAllBySolicitationStatusAndSenderAndDateRange(
      @Param("status") SolicitationStatus.Status status,
      @Param("senderId") UUID senderId,
      @Param("startedAt") LocalDate startedAt,
      @Param("endedAt") LocalDate endedAt);

}
