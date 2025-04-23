package br.com.chronos.server.database.jpa.solicitation.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "attachments")
public class AttachmentModel {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String key;

  @Column(nullable = false)
  private String contentType;

  @OneToOne(mappedBy = "attachment")
  private JustificationModel justification;
}
