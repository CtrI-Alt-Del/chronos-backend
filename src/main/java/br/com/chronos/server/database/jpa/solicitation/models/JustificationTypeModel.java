package br.com.chronos.server.database.jpa.solicitation.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "justification_types")
public class JustificationTypeModel {

  @Id
  private UUID id;

  @Column
  private String name;

  @Column
  private Boolean shouldHaveAttachment;
}
