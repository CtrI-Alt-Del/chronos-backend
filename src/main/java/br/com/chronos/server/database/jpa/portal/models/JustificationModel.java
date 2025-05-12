package br.com.chronos.server.database.jpa.portal.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "justifications")
@Builder
public class JustificationModel {

  @Id
  private UUID id;

  @Column(nullable = true)
  private String description;

  @ManyToOne
  @JoinColumn(name = "justification_type_id", nullable = false)
  private JustificationTypeModel justificationType;

  @OneToMany(mappedBy = "justification")
  @Builder.Default
  private List<ExcusedAbsenceSolicitationModel> excuseAbsenceSolicitations = new ArrayList<>();

  @OneToMany(mappedBy = "justification")
  @Builder.Default
  private List<WithdrawSolicitationModel> withdrawSolicitationModels = new ArrayList<>();

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "attachment_id", nullable = true)
  private AttachmentModel attachment;

}
