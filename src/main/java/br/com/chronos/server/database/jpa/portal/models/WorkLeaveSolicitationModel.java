package br.com.chronos.server.database.jpa.portal.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("WORK_LEAVE")
@EqualsAndHashCode(callSuper = false)
@Table(name = "work_leave_solicitations")
public class WorkLeaveSolicitationModel extends SolicitationModel {
  @Column(nullable = false)
  private LocalDate startedAt;

  @Column(nullable = false)
  private LocalDate endedAt;

  @Column(name = "is_vacation", nullable = false)
  @Builder.Default
  private Boolean isVacation = false;

  @ManyToOne
  @JoinColumn(name = "justification_id", nullable = true)
  private JustificationModel justification;
}