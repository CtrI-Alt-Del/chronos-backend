package br.com.chronos.server.database.jpa.solicitation.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "justifications")
public class JustificationModel {

  @Id
  private UUID id;

  @Column(nullable = true)
  private String description;

  @ManyToOne
  @JoinColumn(name = "justification_type_id", nullable = false)
  private JustificationTypeModel justificationType;

  @OneToOne
  @JoinColumn(name = "attachment_id", nullable = true)
  private AttachmentModel attachment;

}
