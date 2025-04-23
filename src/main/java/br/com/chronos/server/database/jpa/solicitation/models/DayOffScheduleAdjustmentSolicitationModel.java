package br.com.chronos.server.database.jpa.solicitation.models;

import java.util.ArrayList;
import java.util.List;

import br.com.chronos.server.database.jpa.work_schedule.models.DayOffModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("DAY_OFF_SCHEDULE")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
public class DayOffScheduleAdjustmentSolicitationModel extends SolicitationModel {
  @Column(nullable = false)
  private int workDaysCount;

  @Column(nullable = false)
  private int daysOffCount;

  @OneToMany(mappedBy = "dayOffScheduleAdjustment", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  @Default
  private List<DayOffModel> daysOff = new ArrayList<>();
}
