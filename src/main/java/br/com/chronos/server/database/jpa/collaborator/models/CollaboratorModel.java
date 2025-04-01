package br.com.chronos.server.database.jpa.collaborator.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import br.com.chronos.server.database.jpa.auth.models.AccountModel;
import br.com.chronos.server.database.jpa.solicitation.models.TimePunchLogAdjustmentSolicitationModel;
import br.com.chronos.server.database.jpa.solicitation.models.WorkScheduleAdjustmentSolicitationModel;
import br.com.chronos.server.database.jpa.work_schedule.models.DayOffScheduleModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WeekdayScheduleModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkdayLogModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "collaborators")
public class CollaboratorModel {
  @Id
  private UUID id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, unique = true, length = 11)
  private String cpf;

  @OneToOne(mappedBy = "collaborator", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  private AccountModel account;

  @OneToMany(mappedBy = "collaborator", fetch = FetchType.LAZY)
  @Builder.Default
  private List<WeekdayScheduleModel> weekdaySchedules = new ArrayList();

  @OneToOne(mappedBy = "collaborator", fetch = FetchType.LAZY)
  private DayOffScheduleModel dayOffSchedule;

  @OneToMany(mappedBy = "collaborator", fetch = FetchType.LAZY)
  @Builder.Default
  private List<WorkdayLogModel> workdayLogs = new ArrayList();

  @OneToMany(mappedBy = "senderResponsible", fetch = FetchType.LAZY)
  @Builder.Default
  private List<TimePunchLogAdjustmentSolicitationModel> sentTimePunchSolicitations = new ArrayList<>();

  @OneToMany(mappedBy = "replierResponsible", fetch = FetchType.LAZY)
  @Builder.Default
  private List<TimePunchLogAdjustmentSolicitationModel> repliedTimePunchSolicitations = new ArrayList<>();

  @OneToMany(mappedBy = "senderResponsible", fetch = FetchType.LAZY)
  @Builder.Default
  private List<WorkScheduleAdjustmentSolicitationModel> sentWorkScheduleSolicitations = new ArrayList<>();

  @OneToMany(mappedBy = "replierResponsible", fetch = FetchType.LAZY)
  @Builder.Default
  private List<TimePunchLogAdjustmentSolicitationModel> repliedWorkScheduleSolicitations = new ArrayList<>();
}
