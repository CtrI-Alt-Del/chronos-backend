package br.com.chronos.server.database.jpa.portal.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
@DiscriminatorValue("DAY_OFF")
@EqualsAndHashCode(callSuper = false)
@Table(name = "day_off_solicitations")
public class DayOffSolicitationModel extends SolicitationModel {
  @Column(nullable = false)
  private LocalDate dayOff;
}
