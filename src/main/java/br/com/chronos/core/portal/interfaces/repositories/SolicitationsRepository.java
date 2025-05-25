package br.com.chronos.core.portal.interfaces.repositories;

import java.util.Optional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.Month;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.portal.domain.entities.DayOffSolicitation;
import br.com.chronos.core.portal.domain.entities.ExcusedAbsenceSolicitation;
import br.com.chronos.core.portal.domain.entities.Justification;
import br.com.chronos.core.portal.domain.entities.PaidOvertimeSolicitation;
import br.com.chronos.core.portal.domain.entities.TimePunchAdjustmentSolicitation;
import br.com.chronos.core.portal.domain.entities.WorkLeaveSolicitation;
import br.com.chronos.core.portal.domain.records.SolicitationType;
import kotlin.Pair;

public interface SolicitationsRepository {
  Optional<Solicitation> findById(Id id);

  Optional<ExcusedAbsenceSolicitation> findExcusedAbsenceSolicitationById(Id id);

  Optional<TimePunchAdjustmentSolicitation> findTimePunchAdjustmentSolicitationById(Id id);

  Optional<WorkLeaveSolicitation> findWorkLeaveSolicitationById(Id id);

  void addJustificationToSolicitation(ExcusedAbsenceSolicitation solicitation,
      Justification justification);

  void addJustificationToSolicitation(WorkLeaveSolicitation solicitation,
      Justification justification);

  Array<Solicitation> findAllByCollaboratorId(Id collaboratorId);

  Pair<Array<PaidOvertimeSolicitation>, PlusIntegerNumber> findManyPaidOvertimeSolicitationsByCollaborationSector(
      CollaborationSector sector,
      PageNumber page);

  Pair<Array<DayOffSolicitation>, PlusIntegerNumber> findManyDayOffSolicitationsByCollaborationSector(
      CollaborationSector sector,
      PageNumber page);

  Pair<Array<ExcusedAbsenceSolicitation>, PlusIntegerNumber> findManyExcusedAbsenceSolicitationsByCollaborationSector(
      CollaborationSector sector,
      PageNumber page);

  Pair<Array<WorkLeaveSolicitation>, PlusIntegerNumber> findManyWorkLeaveSolicitationsByCollaborationSectorAndVacationStatus(
      CollaborationSector sector,
      Logical isVacation,
      PageNumber page);

  Pair<Array<WorkLeaveSolicitation>, PlusIntegerNumber> findManyWorkLeaveSolicitationSendersByCollaborationSectorAndSenderName(
      CollaborationSector sector,
      Text senderName,
      PageNumber page);

  Array<WorkLeaveSolicitation> findAllApprovedWorkLeaveSolicitationsBySenderAndMonth(
      Id senderId,
      Month month);

  Pair<Array<DayOffScheduleAdjustmentSolicitation>, PlusIntegerNumber> findManyDayOffScheduleAdjustmentSolicitationsByCollaborationSector(
      CollaborationSector sector,
      PageNumber page);

  Pair<Array<TimePunchAdjustmentSolicitation>, PlusIntegerNumber> findManyTimePunchAdjustmentSolicitationsByCollaborationSector(
      CollaborationSector sector,
      PageNumber page);

  Optional<Solicitation> findSolicitationByIdAndSolicitationType(Id solicitationId, SolicitationType type);

  void add(PaidOvertimeSolicitation solicitation);

  void add(WorkLeaveSolicitation solicitation);

  void add(DayOffScheduleAdjustmentSolicitation solicitation);

  void add(ExcusedAbsenceSolicitation solicitation);

  void add(DayOffSolicitation solicitation);

  void add(TimePunchAdjustmentSolicitation solicitation);

  void replace(Solicitation solicitation);

  void replace(DayOffScheduleAdjustmentSolicitation solicitation);

  void replace(PaidOvertimeSolicitation solicitation);

  void replace(DayOffSolicitation solicitation);

  void replace(ExcusedAbsenceSolicitation solicitation);

}