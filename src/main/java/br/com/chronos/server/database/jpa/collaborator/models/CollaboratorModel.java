package br.com.chronos.server.database.jpa.collaborator.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.chronos.core.modules.collaboration.domain.records.CollaboratorRole.Role;
import br.com.chronos.core.modules.collaboration.domain.records.CollaboratorSector.Sector;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkSchedulesModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkdayLogModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "collaborators")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CollaboratorModel{
    @Id
    private UUID id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;
    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "sector", nullable = false)
    private Sector sector;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "work_schedules_id", nullable = false)
    private WorkSchedulesModel workSchedule;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "collaborator", fetch = FetchType.LAZY)
    private Set<WorkdayLogModel> workdayLog = new HashSet<>();
    
}
