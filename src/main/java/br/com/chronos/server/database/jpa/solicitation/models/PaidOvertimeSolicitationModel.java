package br.com.chronos.server.database.jpa.solicitation.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Entity
@DiscriminatorValue("PAID_OVERTIME")
@EqualsAndHashCode(callSuper = false)
public class PaidOvertimeSolicitationModel extends SolicitationModel {

}
