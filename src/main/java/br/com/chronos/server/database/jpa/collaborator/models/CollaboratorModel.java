package br.com.chronos.server.database.jpa.collaborator.models;

import java.util.UUID;

import br.com.chronos.core.modules.collaboration.domain.records.CollaboratorRole.Role;
import br.com.chronos.core.modules.collaboration.domain.records.CollaboratorSector.Sector;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
