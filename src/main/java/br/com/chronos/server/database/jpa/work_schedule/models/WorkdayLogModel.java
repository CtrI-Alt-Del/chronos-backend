package br.com.chronos.server.database.jpa.work_schedule.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import br.com.chronos.core.modules.work_schedule.domain.records.WorkdayStatus.WorkdayStatusName;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.solicitation.models.TimePunchLogAdjustmentSolicitationModel;

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

  @ManyToOne
  @JoinColumn(name = "collaborator_id", nullable = false)
  private CollaboratorModel collaborator;

  @ManyToOne
  @JoinColumn(name = "time_punch_schedule_id", nullable = false)
  private TimePunchModel timePunchSchedule;

  @OneToOne
  @JoinColumn(name = "time_punch_log_id", nullable = false)
  private TimePunchModel timePunchLog;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private WorkdayStatusName status;

  @OneToMany(mappedBy = "workdayLog")
  private List<TimePunchLogAdjustmentSolicitationModel> workdayLogAdjustmentSolicitation;
}
