package br.com.chronos.server.database.jpa.solicitation.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.chronos.core.modules.solicitation.domain.records.SolicitationStatus.Status;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.models.DayOffModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "day_off_schedule_adjustment_solicitations")
public class DayOffScheduleAdjustmentSolicitationModel {

  @Id
  private UUID id;

  @Column(nullable = true)
  private String description;

  @Column(nullable = false)
  private LocalDate requestedAt;

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
  private Status solicitationStatus;

  @Column(nullable = false)
  private int workDaysCount;

  @Column(nullable = false)
  private int daysOffCount;

  @OneToMany(mappedBy = "dayOffScheduleAdjustment",cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
  @Default
  private List<DayOffModel> daysOff = new ArrayList<>();
}
