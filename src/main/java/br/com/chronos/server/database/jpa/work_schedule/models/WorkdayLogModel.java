package br.com.chronos.server.database.jpa.work_schedule.models;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "time_punch_id", nullable = false)
  private TimePunchModel timePunch;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private WorkdayStatusName status;

  @Column(name = "workload_schedule", nullable = false)
  private byte workloadSchedule;

  // @OneToMany(mappedBy = "workdayLog", fetch = FetchType.LAZY)
  // private List<WorkdayLogAdjustmentSolicitationModel>
  // workdayLogAdjustmentSolicitation;
}
