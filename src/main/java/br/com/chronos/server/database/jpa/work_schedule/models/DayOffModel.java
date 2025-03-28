package br.com.chronos.server.database.jpa.work_schedule.models;

import java.time.LocalDate;
import java.util.UUID;

import br.com.chronos.server.database.jpa.solicitation.models.WorkScheduleAdjustmentSolicitationModel;
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

  @ManyToOne
  @JoinColumn(name = "work_schedule_id")
  private WorkScheduleModel workSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_schedule_id")
    private WorkScheduleModel workSchedule;
}
