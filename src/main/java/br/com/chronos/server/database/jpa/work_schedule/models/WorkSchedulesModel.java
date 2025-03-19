package br.com.chronos.server.database.jpa.work_schedule.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "work_schedules")
public class WorkSchedulesModel {
    @Id
    private UUID id;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "workSchedule", fetch = FetchType.LAZY)
    private Set<WorkdayOffModel> workdayOff = new HashSet<>();
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "workSchedule", fetch = FetchType.LAZY)
    private Set<TimePunchScheduleModel> timePunchSchule = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "workSchedule", fetch = FetchType.LAZY)
    private Set<CollaboratorModel> collaborator = new HashSet<>();
}
