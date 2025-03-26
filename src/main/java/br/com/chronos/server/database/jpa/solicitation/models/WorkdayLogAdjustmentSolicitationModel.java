package br.com.chronos.server.database.jpa.solicitation.models;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

import br.com.chronos.core.modules.solicitation.domain.records.SolicitationStatus;
import br.com.chronos.core.modules.work_schedule.domain.records.TimePunchPeriod;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkdayLogModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "workday_log_adjustment_requests")
public class WorkdayLogAdjustmentSolicitationModel {
  @Id
  private UUID id;

  @Column(nullable = true)
  private String description;

  @Column(nullable = false)
  private Date request_at;

  @Column(nullable = true)
  private String feedbackMessage;

  @ManyToOne
  @JoinColumn(name = "sender_responsible_id", nullable = false)
  private CollaboratorModel senderResponsible; 

  @ManyToOne
  @JoinColumn(name = "replier_responsible_id")
  private CollaboratorModel replierResponsible;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "status")
  private SolicitationStatus solicitationStatus;

  @ManyToOne
  @JoinColumn(name = "workday_log_id", nullable = false)
  private WorkdayLogModel workdayLog;

  @Column(nullable = false)
  private LocalTime time;

  @Enumerated(EnumType.STRING)
  @Column(name = "time_punch_period", nullable = false)
  private TimePunchPeriod timePunchPeriod;
}
