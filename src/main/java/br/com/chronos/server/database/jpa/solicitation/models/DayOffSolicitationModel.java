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
@DiscriminatorValue("DAY_OFF")
@EqualsAndHashCode(callSuper = false)
public class DayOffSolicitationModel extends SolicitationModel {


  @Column(nullable = false)
  private LocalDate dayOff;

  @ManyToOne
  @JoinColumn(name = "justification_id", nullable = false)
  private JustificationModel justification;

}
