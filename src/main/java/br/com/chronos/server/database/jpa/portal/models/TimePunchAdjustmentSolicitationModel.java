package br.com.chronos.server.database.jpa.portal.models;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.chronos.core.portal.domain.records.TimePunchAdjustmentReason.Reason;
import br.com.chronos.core.work_schedule.domain.records.TimePunchPeriod.PeriodName;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("TIME_PUNCH_ADJUSTMENT")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "time_punch_adjusment_solicitations")
public class TimePunchAdjustmentSolicitationModel extends SolicitationModel {
  @Column(name = "date")
  private LocalDate date;

  @Column(nullable = false)
  private LocalTime time;

  @Enumerated(EnumType.STRING)
  @Column(name = "time_punch_period", nullable = false)
  private PeriodName timePunchPeriod;

  @Enumerated(EnumType.STRING)
  @Column(name = "reason", nullable = false)
  private Reason reason;
}
