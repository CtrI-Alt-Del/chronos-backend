package br.com.chronos.server.database.jpa.portal.models;

import java.time.LocalDate;
import java.util.UUID;

import br.com.chronos.core.portal.domain.records.SolicitationStatus.Status;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "solicitation_type")
@Table(name = "solicitations")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public abstract class SolicitationModel {
  @Id
  private UUID id;

  @Column(nullable = true)
  private String description;

  @Column(nullable = false)
  private LocalDate date;

  @ManyToOne
  @JoinColumn(name = "sender_responsible_id", nullable = false)
  private CollaboratorModel senderResponsible;

  @ManyToOne
  @JoinColumn(name = "replier_responsible_id", nullable = true)
  private CollaboratorModel replierResponsible;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status solicitationStatus;

  @Column(nullable = true)
  private String feedbackMessage;

}
