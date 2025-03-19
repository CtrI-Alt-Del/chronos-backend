package br.com.chronos.server.database.jpa.work_schedule.models;

import java.util.UUID;

import br.com.chronos.core.modules.global.domain.records.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "workday_off")
public class WorkdayOffModel {
    @Id
    private UUID id;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "work_schedule_id", nullable = false)
    private WorkSchedulesModel workSchedule;
}
