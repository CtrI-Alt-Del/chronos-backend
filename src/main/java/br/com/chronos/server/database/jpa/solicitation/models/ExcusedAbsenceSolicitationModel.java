package br.com.chronos.server.database.jpa.solicitation.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("EXCUSED_ABSENCE")
@EqualsAndHashCode(callSuper = false)
@Table(name = "excused_absence_solicitations")
public class ExcusedAbsenceSolicitationModel extends SolicitationModel {
  @Column(nullable = false)
  private LocalDate absenceDate;
  @ManyToOne
  @JoinColumn(name = "justification_id", nullable = true)
  private JustificationModel justification;

}
