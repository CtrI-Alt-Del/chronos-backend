package br.com.chronos.server.database.jpa.solicitation.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@DiscriminatorValue("EXCUSE_ABSENCE")
@EqualsAndHashCode(callSuper = false)
public class ExcuseAbsenceSolicitationModel extends SolicitationModel {
  @Column(nullable = false)
  private LocalDate excuseAbsenceDate;
  @ManyToOne
  @JoinColumn(name = "justification_id", nullable = true)
  private JustificationModel justification;

}
