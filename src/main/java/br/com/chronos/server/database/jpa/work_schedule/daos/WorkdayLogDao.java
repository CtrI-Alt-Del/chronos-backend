package br.com.chronos.server.database.jpa.work_schedule.daos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.work_schedule.domain.records.WorkdayStatus.WorkdayStatusName;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkdayLogModel;

public interface WorkdayLogDao extends JpaRepository<WorkdayLogModel, UUID> {
  Optional<WorkdayLogModel> findByDate(LocalDate date);

  List<WorkdayLogModel> findAllByDate(LocalDate date);

  @Query("""
      SELECT wl FROM WorkdayLogModel wl
      LEFT JOIN FETCH wl.collaborator c
      WHERE c.id = :collaboratorId and wl.date BETWEEN :startDate AND :endDate
      ORDER BY wl.date DESC
      """)
  Page<WorkdayLogModel> findAllByCollaboratorAndDateBetween(
      @Param("collaboratorId") UUID collaboratorId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      PageRequest pageRequest);

  Optional<WorkdayLogModel> findByCollaboratorAndDate(CollaboratorModel collaborator, LocalDate Date);

  @Query(value = """
          SELECT
            EXTRACT(MONTH FROM wl.date) AS month,
            SUM(CASE WHEN a.role <> 'MANAGER' THEN 1 ELSE 0 END) AS collaboratorAbsence,
            SUM(CASE WHEN a.role = 'MANAGER' THEN 1 ELSE 0 END) AS managerAbsence
          FROM workday_logs wl
          JOIN collaborators c ON wl.collaborator_id = c.id
          JOIN accounts a ON c.id = a.collaborator_id
          WHERE wl.status = :status
            AND wl.date BETWEEN :startDate AND :endDate
          GROUP BY month
          ORDER BY month
      """, nativeQuery = true)
  List<Object[]> countMonthlyAbsencesByRole(
      @Param("status") String status,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  long countByStatusAndDateBetweenOrderByDateDesc(
      WorkdayStatusName status,
      LocalDate startDate,
      LocalDate endDate);

  List<WorkdayLogModel> findAllByCollaboratorAndDateBetweenOrderByDateDesc(
      CollaboratorModel collaborator,
      LocalDate starDate,
      LocalDate endDate);

  Page<WorkdayLogModel> findAllByDateAndCollaboratorNameContainingIgnoreCaseAndCollaboratorAccountSector(
      LocalDate date,
      String collaboratorName,
      CollaborationSector.Sector collaborationSector,
      PageRequest pageRequest);

  @Query(value = "SELECT EXISTS (SELECT 1 FROM workday_logs WHERE time_punch_id = :timePunchId)", nativeQuery = true)
  boolean timePunchLogExists(@Param("timePunchId") UUID timePunchId);

  @Modifying
  @Query(value = "DELETE FROM workday_logs WHERE date = :date", nativeQuery = true)
  void deleteAllByDate(LocalDate date);
}
