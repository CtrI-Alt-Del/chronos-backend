package br.com.chronos.server.database.jpa.work_schedule.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import br.com.chronos.core.work_schedule.domain.records.WorkdayStatus.WorkdayStatusName;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "workday_logs")
public class WorkdayLogModel {
  @Id
  private UUID id;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "collaborator_id", nullable = false)
  private CollaboratorModel collaborator;

  @Column(name = "first_clock_in", nullable = true)
  private LocalTime firstClockIn;

  @Column(name = "first_clock_out", nullable = true)
  private LocalTime firstClockOut;

  @Column(name = "second_clock_in", nullable = true)
  private LocalTime secondClockIn;

  @Column(name = "second_clock_out", nullable = true)
  private LocalTime secondClockOut;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private WorkdayStatusName status;

  @Column(name = "workload_schedule", nullable = false)
  private byte workloadSchedule;

  @Column(name = "hour_bank_credit", nullable = false)
  private LocalTime hourBankCredit;

  @Column(name = "hour_bank_debit", nullable = false)
  private LocalTime hourBankDebit;

  // @OneToMany(mappedBy = "workdayLog", fetch = FetchType.LAZY)
  // private List<WorkdayLogAdjustmentSolicitationModel>
  // workdayLogAdjustmentSolicitation;
}
