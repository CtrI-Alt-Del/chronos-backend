package br.com.chronos.server.database.jpa.portal.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("DAY_OFF_SCHEDULE")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "day_off_schedule_adjustment_solicitations")
public class DayOffScheduleAdjustmentSolicitationModel extends SolicitationModel {
  @Column(nullable = false)
  private int workDaysCount;

  @Column(nullable = false)
  private int daysOffCount;

  @Column(nullable = false)
  private List<LocalDate> daysOff;
}
