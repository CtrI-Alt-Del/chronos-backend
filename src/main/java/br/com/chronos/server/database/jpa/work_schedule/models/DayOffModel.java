package br.com.chronos.server.database.jpa.work_schedule.models;

import java.time.LocalDate;
import java.util.UUID;

import br.com.chronos.server.database.jpa.portal.models.DayOffScheduleAdjustmentSolicitationModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "days_off")
public class DayOffModel {
  @Id
  private UUID id;

  @Column(nullable = false)
  private LocalDate date;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "day_off_schedule_id", nullable = true)
  private DayOffScheduleModel dayOffSchedule;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "day_off_schedule_adjustment_id", nullable = true)
  private DayOffScheduleAdjustmentSolicitationModel dayOffScheduleAdjustment;
}
