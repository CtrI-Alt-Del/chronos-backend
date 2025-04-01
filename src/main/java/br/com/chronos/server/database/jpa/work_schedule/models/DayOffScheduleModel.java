package br.com.chronos.server.database.jpa.work_schedule.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "day_off_schedules")
public class DayOffScheduleModel {
  @Id
  private UUID id;

  @Column(nullable = false)
  private int workDaysCount;

  @Column(nullable = false)
  private int daysOffCount;

  @OneToMany(mappedBy = "dayOffSchedule", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  @Default
  private List<DayOffModel> daysOff = new ArrayList<>();
}
