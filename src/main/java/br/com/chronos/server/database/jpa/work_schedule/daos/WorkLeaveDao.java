package br.com.chronos.server.database.jpa.work_schedule.daos;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.chronos.server.database.jpa.work_schedule.models.WorkLeaveModel;
import io.lettuce.core.dynamic.annotation.Param;

public interface WorkLeaveDao extends JpaRepository<WorkLeaveModel, UUID> {
  @Query("""
      SELECT wl FROM WorkLeaveModel wl
      JOIN FETCH wl.collaborator c
      WHERE c.id = :collaboratorId AND :date BETWEEN wl.startedAt AND wl.endedAt
      """)
  Optional<WorkLeaveModel> findByCollaboratorAndDate(
      @Param("collaboratorId") UUID collaboratorId,
      @Param("date") LocalDate date);
}