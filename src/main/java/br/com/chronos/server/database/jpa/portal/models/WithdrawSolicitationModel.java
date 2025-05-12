
package br.com.chronos.server.database.jpa.portal.models;

import java.time.LocalDate;
import java.util.List;

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
@DiscriminatorValue("WITHDRAW")
@EqualsAndHashCode(callSuper = false)
@Table(name = "withdraw_solicitations")
public class WithdrawSolicitationModel extends SolicitationModel {
  @Column(nullable = false)
  private List<LocalDate> withdrawalDays;
  @ManyToOne
  @JoinColumn(name = "justification_id", nullable = true)
  private JustificationModel justification;

}
