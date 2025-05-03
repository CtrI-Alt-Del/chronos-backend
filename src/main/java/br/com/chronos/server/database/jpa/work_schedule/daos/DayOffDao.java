package br.com.chronos.server.database.jpa.work_schedule.daos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.chronos.server.database.jpa.work_schedule.models.DayOffModel;

public interface DayOffDao extends JpaRepository<DayOffModel, UUID> {
  @Modifying
  @Query(value = "DELETE FROM days_off WHERE day_off_schedule_id = :dayOffScheduleId", nativeQuery = true)
  void deleteManyByDayOffSchedule(@Param("dayOffScheduleId") UUID dayOffScheduleId);
}