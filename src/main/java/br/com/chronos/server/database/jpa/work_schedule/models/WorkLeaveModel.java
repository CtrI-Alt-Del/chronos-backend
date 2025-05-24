package br.com.chronos.server.database.jpa.work_schedule.models;

import java.time.LocalDate;
import java.util.UUID;

import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "work_leaves")
public class WorkLeaveModel {
  @Id
  private UUID id;

  @Column(name = "started_at")
  private LocalDate startedAt;

  @Column(name = "ended_at")
  private LocalDate endedAt;

  @Column(name = "is_vacation")
  private boolean isVacation;

  @ManyToOne
  @JoinColumn(name = "collaborator_id")
  private CollaboratorModel collaborator;
}
