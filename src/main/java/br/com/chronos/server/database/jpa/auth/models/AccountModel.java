package br.com.chronos.server.database.jpa.auth.models;

import java.util.UUID;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.modules.global.domain.records.Role.RoleName;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class AccountModel {
  @Id
  private UUID id;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  @Builder.Default
  private Boolean isActive = true;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RoleName role;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Sector sector;

  @OneToOne
  @JoinColumn(name = "collaborator_id", nullable = true)
  private CollaboratorModel collaborator;
}
