package br.com.chronos.server.database.jpa.work_schedule.models;

import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @OneToOne(mappedBy = "timePunch", fetch = FetchType.LAZY)
  private WorkdayLogModel workdayLog;
}
