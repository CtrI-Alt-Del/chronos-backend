package br.com.chronos.server.database.jpa.work_schedule.models;

import java.time.LocalDate;
import java.util.UUID;

import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class WorkLeaveModel {
  @Id
  private UUID id;

  @Column(name = "started_at", nullable = false)
  private LocalDate startedAt;

  @Column(name = "ended_at", nullable = false)
  private LocalDate endedAt;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "collaborator_id", nullable = false)
  private CollaboratorModel collaborator;
}