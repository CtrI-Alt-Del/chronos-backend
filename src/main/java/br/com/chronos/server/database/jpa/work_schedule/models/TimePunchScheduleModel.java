package br.com.chronos.server.database.jpa.work_schedule.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.chronos.core.modules.work_schedule.domain.records.Weekday.WeekdayName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "time_punch-schedules")
public class TimePunchScheduleModel {
    @Id
    private UUID id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "weekday", nullable = false)
    private WeekdayName weekdayName;

    @ManyToOne
    @JoinColumn(name = "work_schedule_id", nullable = false)
    private WorkSchedulesModel workSchedule;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "time_punch_id")
    private TimePunchModel timePunch;

    @OneToOne(mappedBy = "timePunchSchedule")
    private WorkdayLogModel workdayLog;
}

