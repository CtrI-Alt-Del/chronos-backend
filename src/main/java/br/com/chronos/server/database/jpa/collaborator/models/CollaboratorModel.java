package br.com.chronos.server.database.jpa.collaborator.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import lombok.Builder.Default;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.modules.global.domain.records.Role.RoleName;
import br.com.chronos.server.database.jpa.auth.models.AccountModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkSchedulesModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WorkdayLogModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "collaborators")
public class CollaboratorModel {
    @Id
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sector sector;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName role;

    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountModel account;

    @ManyToOne
    @JoinColumn(name = "work_schedule_id", nullable = true)
    private WorkSchedulesModel workSchedule;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "collaborator", fetch = FetchType.LAZY)
    @Default
    private Set<WorkdayLogModel> workdayLog = new HashSet<>();

}
