package br.com.chronos.server.database.jpa.solicitation.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import kotlin.Pair;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.solicitation.domain.entities.DayOffSolicitation;
import br.com.chronos.core.solicitation.domain.entities.ExcusedAbsenceSolicitation;
import br.com.chronos.core.solicitation.domain.entities.PaidOvertimeSolicitation;
import br.com.chronos.core.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.solicitation.domain.records.SolicitationType;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffScheduleAdjustmentRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.DayOffSolicitationRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.core.solicitation.interfaces.repositories.TimePunchLogAdjustmentRepository;
import br.com.chronos.server.database.jpa.solicitation.daos.DayOffSolicitationDao;
import br.com.chronos.server.database.jpa.solicitation.daos.ExcusedAbsenceSolicitationDao;
import br.com.chronos.server.database.jpa.solicitation.daos.PaidOvertimeSolicitationDao;
import br.com.chronos.server.database.jpa.solicitation.daos.SolicitationDao;
import br.com.chronos.server.database.jpa.solicitation.mappers.DayOffSolicitationMapper;
import br.com.chronos.server.database.jpa.solicitation.mappers.ExcusedAbsenceSolicitationMapper;
import br.com.chronos.server.database.jpa.solicitation.mappers.PaidOvertimeSolicitationMapper;
import br.com.chronos.server.database.jpa.solicitation.mappers.SolicitationMapper;

public class JpaSolicitationsRepository implements SolicitationsRepository {
  @Autowired
  private SolicitationDao solicitationDao;

  @Autowired
  private PaidOvertimeSolicitationDao paidOvertimeSolicitationDao;

  @Autowired
  private PaidOvertimeSolicitationMapper paidOvertimeSolicitationMapper;

  @Autowired
  private ExcusedAbsenceSolicitationDao excusedAbsenceSolicitationDao;

  @Autowired
  private ExcusedAbsenceSolicitationMapper excusedAbsenceSolicitationMapper;

  @Autowired
  private DayOffSolicitationDao dayOffSolicitationDao;

  @Autowired
  private DayOffSolicitationMapper dayOffSolicitationMapper;

  @Autowired
  private TimePunchLogAdjustmentRepository timePunchLogAdjustmentSolicitationModelsRepository;

  @Autowired
  private DayOffScheduleAdjustmentRepository dayOffScheduleAdjustmentSolcitationModelsRepository;

  @Autowired
  private DayOffSolicitationRepository dayOffSolicitationRepository;

  @Autowired
  private SolicitationMapper solicitationMapper;

  @Override
  public Optional<Solicitation> findById(Id solicitationId) {
    var solicitationModel = solicitationDao.findById(solicitationId.value());
    if (solicitationModel.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(solicitationMapper.toEntity(solicitationModel.get()));
  }

  @Override
  public Optional<PaidOvertimeSolicitation> findPaidOvertimeSolicitationById(Id solicitationId) {
    var solicitationModel = paidOvertimeSolicitationDao.findById(solicitationId.value());
    if (solicitationModel.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(paidOvertimeSolicitationMapper.toEntity(solicitationModel.get()));
  }

  @Override
  public void resolveSolicitation(Solicitation solicitation) {
    if (solicitation.getType().isTimePunch().isTrue()) {

      TimePunchLogAdjustmentSolicitation timePunchSolicitation = (TimePunchLogAdjustmentSolicitation) solicitation;
      timePunchLogAdjustmentSolicitationModelsRepository.resolveSolicitation(timePunchSolicitation);

    } else if (solicitation.getType().isDayOffSchedule().isTrue()) {

      DayOffScheduleAdjustmentSolicitation dayOffScheduleSolicitation = (DayOffScheduleAdjustmentSolicitation) solicitation;
      dayOffScheduleAdjustmentSolcitationModelsRepository.resolveSolicitation(dayOffScheduleSolicitation);

    } else if (solicitation.getType().isDayOff().isTrue()) {
      DayOffSolicitation dayOffSolicitation = (DayOffSolicitation) solicitation;
      dayOffSolicitationRepository.resolveSolicitation(dayOffSolicitation);
    }
  }

  @Override
  public Optional<Solicitation> findSolicitationByIdAndSolicitationType(Id solicitationId, SolicitationType type) {
    var solicitationModel = solicitationDao.findById(solicitationId.value());
    if (solicitationModel.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(solicitationMapper.toEntity(solicitationModel.get()));
  }

  @Override
  public Array<Solicitation> findAllByCollaboratorId(Id collaboratorId) {
    var solicitationModels = solicitationDao.findAllBySenderResponsibleId(collaboratorId.value());
    var solicitations = Array.createFrom(solicitationModels, solicitationMapper::toEntity);
    return solicitations;
  }

  @Override
  public Array<Solicitation> findAllByCollaboratorSector(CollaborationSector sector) {
    var solicitationModels = solicitationDao.findAllBySenderResponsibleAccountSector(sector.value());
    var solicitations = Array.createFrom(solicitationModels, solicitationMapper::toEntity);
    return solicitations;
  }

  @Override
  public Pair<Array<PaidOvertimeSolicitation>, PlusIntegerNumber> findManyPaidOvertimeSolicitationsByCollaborationSector(
      CollaborationSector sector,
      PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var models = paidOvertimeSolicitationDao.findAllBySenderResponsibleAccountSectorOrderByDateDesc(
        sector.value(), pageRequest);
    var items = models.stream().toList();
    var itemsCount = models.getTotalElements();

    return new Pair<>(
        Array.createFrom(items, paidOvertimeSolicitationMapper::toEntity),
        PlusIntegerNumber.create((int) itemsCount));
  }

  @Override
  public void add(PaidOvertimeSolicitation solicitation) {
    var solicitationModel = paidOvertimeSolicitationMapper.toModel(solicitation);
    paidOvertimeSolicitationDao.save(solicitationModel);
  }

  @Override
  public void add(ExcusedAbsenceSolicitation solicitation) {
    var solicitationModel = excusedAbsenceSolicitationMapper.toModel(solicitation);
    excusedAbsenceSolicitationDao.save(solicitationModel);
  }

  @Override
  public void add(DayOffSolicitation solicitation) {
    var solicitationModel = dayOffSolicitationMapper.toModel(solicitation);
    dayOffSolicitationDao.save(solicitationModel);
  }

  @Override
  public void replace(Solicitation solicitation) {
    var model = solicitationMapper.toModel(solicitation);
    solicitationDao.save(model);
  }

  @Override
  public void replace(PaidOvertimeSolicitation solicitation) {
    var model = paidOvertimeSolicitationMapper.toModel(solicitation);
    paidOvertimeSolicitationDao.save(model);
  }

  @Override
  public void replace(DayOffSolicitation solicitation) {
    var model = dayOffSolicitationMapper.toModel(solicitation);
    dayOffSolicitationDao.save(model);
  }

  @Override
  public void replace(ExcusedAbsenceSolicitation solicitation) {
    var model = excusedAbsenceSolicitationMapper.toModel(solicitation);
    excusedAbsenceSolicitationDao.save(model);
  }

  @Override
  public Optional<ExcusedAbsenceSolicitation> findExcusedAbsenceSolicitationById(Id solicitationId) {
    var model = excusedAbsenceSolicitationDao.findById(solicitationId.value());
    if (model.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(excusedAbsenceSolicitationMapper.toEntity(model.get()));
  }

  @Override
  public Pair<Array<ExcusedAbsenceSolicitation>, PlusIntegerNumber> findManyExcusedAbsenceSolicitationsByCollaborationSector(
      CollaborationSector sector, PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var models = excusedAbsenceSolicitationDao.findAllBySenderResponsibleAccountSectorOrderByDateDesc(
        sector.value(),
        pageRequest);
    var items = models.stream().toList();
    var itemsCount = models.getTotalElements();

    return new Pair<>(
        Array.createFrom(items, excusedAbsenceSolicitationMapper::toEntity),
        PlusIntegerNumber.create((int) itemsCount));
  }

  @Override
  public Pair<Array<DayOffSolicitation>, PlusIntegerNumber> findManyDayOffSolicitationsByCollaborationSector(
      CollaborationSector sector, PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var models = dayOffSolicitationDao.findAllBySenderResponsibleAccountSectorOrderByDateDesc(
        sector.value(),
        pageRequest);
    var items = models.stream().toList();
    var itemsCount = models.getTotalElements();

    return new Pair<>(
        Array.createFrom(items, dayOffSolicitationMapper::toEntity),
        PlusIntegerNumber.create((int) itemsCount));
  }

}
