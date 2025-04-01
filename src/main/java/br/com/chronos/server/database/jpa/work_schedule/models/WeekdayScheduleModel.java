package br.com.chronos.server.database.jpa.work_schedule.models;

import java.util.UUID;

import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import br.com.chronos.core.modules.work_schedule.domain.records.Weekday.WeekdayName;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "weekday_schedules")
public class WeekdayScheduleModel {
  @Id
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "weekday", nullable = false)
  private WeekdayName weekdayName;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "time_punch_id", nullable = false)
  private TimePunchModel timePunch;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "collaborator_id", nullable = false)
  private CollaboratorModel collaborator;
}
