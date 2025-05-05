package br.com.chronos.server.database.jpa.portal.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("PAID_OVERTIME")
@EqualsAndHashCode(callSuper = false)
@Table(name = "paid_overtime_solicitations")
public class PaidOvertimeSolicitationModel extends SolicitationModel {

}
