
package br.com.chronos.server.database.jpa.portal.models;

import java.time.LocalDate;
import java.util.List;

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
@DiscriminatorValue("VACATION")
@EqualsAndHashCode(callSuper = false)
@Table(name = "vacation_solicitations")
public class VacationSolicitationModel extends SolicitationModel {
  @Column(nullable = false)
  private List<LocalDate> vacationDays;
}
