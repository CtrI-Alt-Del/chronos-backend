package br.com.chronos.server.database.jpa.work_schedule.models;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "time_punches")
public class TimePunchModel {
  @Id
  private UUID id;

  @Column(name = "first_clock_in", nullable = true)
  private LocalTime firstClockIn;

  @Column(name = "first_clock_out", nullable = true)
  private LocalTime firstClockOut;

  @Column(name = "second_clock_in", nullable = true)
  private LocalTime secondClockIn;

  @Column(name = "second_clock_out", nullable = true)
  private LocalTime secondClockOut;

  @OneToOne(mappedBy = "timePunch", fetch = FetchType.EAGER)
  private WeekdayScheduleModel weekdaySchedule;

  @OneToOne(mappedBy = "timePunchLog", fetch = FetchType.LAZY)
  private WorkdayLogModel workdayLog;

  @OneToMany(mappedBy = "timePunchSchedule", fetch = FetchType.LAZY)
  @Default
  private List<WorkdayLogModel> workdayLogs = new ArrayList<>();
}
