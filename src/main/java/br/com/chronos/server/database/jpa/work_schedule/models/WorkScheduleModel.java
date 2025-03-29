package br.com.chronos.server.database.jpa.work_schedule.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.solicitation.models.WorkScheduleAdjustmentSolicitationModel;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "work_schedules")
public class WorkScheduleModel {
    @Id
    private UUID id;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false)
    private int workDaysCount;

    @Column(nullable = false)
    private int daysOffCount;

    @OneToMany(mappedBy = "workSchedule", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Default
    private List<DayOffModel> daysOff = new ArrayList<>();

    @OneToMany(mappedBy = "workSchedule", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Default
    private List<WeekdayScheduleModel> weekdaySchedules = new ArrayList<>();

    @OneToMany(mappedBy = "workSchedule", fetch = FetchType.LAZY)
    @Default
    private List<CollaboratorModel> collaborators = new ArrayList<>();
}
