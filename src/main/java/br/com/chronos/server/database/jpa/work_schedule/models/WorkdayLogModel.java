package br.com.chronos.server.database.jpa.work_schedule.models;

import java.time.LocalDate;
import java.util.UUID;

import br.com.chronos.core.modules.work_schedule.domain.records.WorkdayStatus.WorkdayStatusName;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import jakarta.persistence.EnumType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "workday_logs")
public class WorkdayLogModel {
    @Id
    private UUID id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "collaborator_id", nullable = false)
    private CollaboratorModel collaborator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_punch_schedule_id")
    private TimePunchModel timePunchSchedule;

    @OneToOne
    @JoinColumn(name = "time_punch_log_id")
    private TimePunchModel timePunchLog;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WorkdayStatusName status;
}
