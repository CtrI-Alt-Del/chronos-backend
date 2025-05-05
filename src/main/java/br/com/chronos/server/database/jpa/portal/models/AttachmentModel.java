package br.com.chronos.server.database.jpa.portal.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
  @Column(nullable = false)
  private String id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String contentType;

  @OneToOne(mappedBy = "attachment")
  private JustificationModel justification;
}
