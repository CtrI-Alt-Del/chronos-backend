// package br.com.chronos.server.database.jpa.solicitation.models;
//
// import java.util.UUID;
//
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToOne;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;
//
// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// @Entity
// @Table(name = "justifications")
// public class JustificationModel {
//
//   @Id
//   private UUID id;
//
//   @Column(nullable = true)
//   private String description;
//
//   @OneToOne(mappedBy = "justification")
//   private WorkdayLogAdjustmentSolicitationModel workdayLogAdjustmentSolicitation;
//
// }
