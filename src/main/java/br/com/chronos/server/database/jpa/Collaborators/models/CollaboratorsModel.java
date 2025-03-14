package br.com.chronos.server.database.jpa.Collaborators.models;

import java.io.Serializable;
import br.com.chronos.core.modules.collaboration.domain.records.CollaboratorRole.Role;
import br.com.chronos.core.modules.collaboration.domain.records.CollaboratorSector.Sector;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;

@Entity
@Table(name = "Collaborators")
@Data
public class CollaboratorsModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    @Column(name = "CPF", nullable = false, unique = true, length = 14)
    private String CPF;
    @Column(name = "password", nullable = false, unique = true, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "sector", nullable = false)
    private Sector sector;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    public CollaboratorsModel(String name, String email, String CPF, 
    String password, Sector sector, Role role, boolean isActive) {
        this.name = name;
        this.email = email;
        this.CPF = CPF;
        this.password = password;
        this.sector = sector;
        this.role = role;
        this.isActive = isActive;
    }

}
