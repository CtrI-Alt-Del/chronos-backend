package br.com.chronos.server.database.jpa.portal.repositories;

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
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
import br.com.chronos.core.portal.domain.entities.DayOffScheduleAdjustmentSolicitation;
import br.com.chronos.core.portal.domain.entities.DayOffSolicitation;
import br.com.chronos.core.portal.domain.entities.ExcusedAbsenceSolicitation;
import br.com.chronos.core.portal.domain.entities.Justification;
import br.com.chronos.core.portal.domain.entities.PaidOvertimeSolicitation;
import br.com.chronos.core.portal.domain.entities.TimePunchAdjustmentSolicitation;
import br.com.chronos.core.portal.domain.entities.VacationSolicitation;
import br.com.chronos.core.portal.domain.entities.WithdrawSolicitation;
import br.com.chronos.core.portal.domain.records.SolicitationType;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.server.database.jpa.portal.daos.DayOffScheduleAdjustmentSolicitationDao;
import br.com.chronos.server.database.jpa.portal.daos.DayOffSolicitationDao;
import br.com.chronos.server.database.jpa.portal.daos.ExcusedAbsenceSolicitationDao;
import br.com.chronos.server.database.jpa.portal.daos.PaidOvertimeSolicitationDao;
import br.com.chronos.server.database.jpa.portal.daos.SolicitationDao;
import br.com.chronos.server.database.jpa.portal.daos.TimePunchAdjustmentSolicitationDao;
import br.com.chronos.server.database.jpa.portal.daos.VacationSolicitationDao;
import br.com.chronos.server.database.jpa.portal.daos.WithdrawSolicitationDao;
import br.com.chronos.server.database.jpa.portal.mappers.DayOffScheduleAdjustmentSolicitationMapper;
import br.com.chronos.server.database.jpa.portal.mappers.DayOffSolicitationMapper;
import br.com.chronos.server.database.jpa.portal.mappers.ExcusedAbsenceSolicitationMapper;
import br.com.chronos.server.database.jpa.portal.mappers.JustificationMapper;
import br.com.chronos.server.database.jpa.portal.mappers.PaidOvertimeSolicitationMapper;
import br.com.chronos.server.database.jpa.portal.mappers.SolicitationMapper;
import br.com.chronos.server.database.jpa.portal.mappers.TimePunchAdjustmentSolicitationMapper;
import br.com.chronos.server.database.jpa.portal.mappers.VacationSolicitationMapper;
import br.com.chronos.server.database.jpa.portal.mappers.WithdrawSolicitationMapper;

public class JpaSolicitationsRepository implements SolicitationsRepository {
  @Autowired
  private SolicitationDao solicitationDao;

  @Autowired
  private JustificationMapper justificationMapper;

  @Autowired
  private DayOffScheduleAdjustmentSolicitationDao dayOffScheduleAdjustmentSolicitationDao;

  @Autowired
  private DayOffScheduleAdjustmentSolicitationMapper dayOffScheduleAdjustmentSolicitationMapper;

  @Autowired
  private PaidOvertimeSolicitationDao paidOvertimeSolicitationDao;

  @Autowired
  private PaidOvertimeSolicitationMapper paidOvertimeSolicitationMapper;

  @Autowired
  private ExcusedAbsenceSolicitationDao excusedAbsenceSolicitationDao;

  @Autowired
  private TimePunchAdjustmentSolicitationDao timePunchAdjustmentSolicitationDao;

  @Autowired
  private TimePunchAdjustmentSolicitationMapper timePunchAdjustmentSolicitationMapper;

  @Autowired
  private ExcusedAbsenceSolicitationMapper excusedAbsenceSolicitationMapper;

  @Autowired
  private DayOffSolicitationDao dayOffSolicitationDao;

  @Autowired
  private DayOffSolicitationMapper dayOffSolicitationMapper;

  @Autowired
  private VacationSolicitationDao vacationSolicitationDao;

  @Autowired
  private VacationSolicitationMapper vacationSolicitationMapper;

  @Autowired
  private SolicitationMapper solicitationMapper;

  @Autowired
  private WithdrawSolicitationDao withdrawSolicitationDao;

  @Autowired
  private WithdrawSolicitationMapper withdrawSolicitationMapper;

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
  public Optional<TimePunchAdjustmentSolicitation> findTimePunchAdjustmentSolicitationById(Id solicitationId) {
    var solicitationModel = timePunchAdjustmentSolicitationDao.findById(solicitationId.value());
    if (solicitationModel.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(timePunchAdjustmentSolicitationMapper.toEntity(solicitationModel.get()));
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

  @Override
  public void addJustificationToSolicitation(ExcusedAbsenceSolicitation solicitation, Justification justification) {
    var solicitationModel = excusedAbsenceSolicitationMapper.toModel(solicitation);
    var justificationModel = justificationMapper.toModel(justification);
    solicitationModel.setJustification(justificationModel);
    excusedAbsenceSolicitationDao.save(solicitationModel);
  }

  @Override
  public Pair<Array<DayOffScheduleAdjustmentSolicitation>, PlusIntegerNumber> findManyDayOffScheduleAdjustmentSolicitationsByCollaborationSector(
      CollaborationSector sector, PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var models = dayOffScheduleAdjustmentSolicitationDao.findAllBySenderResponsibleAccountSectorOrderByDateDesc(
        sector.value(),
        pageRequest);
    var items = models.stream().toList();
    var itemsCount = models.getTotalElements();

    return new Pair<>(
        Array.createFrom(items, dayOffScheduleAdjustmentSolicitationMapper::toEntity),
        PlusIntegerNumber.create((int) itemsCount));
  }

  @Override
  public void replace(DayOffScheduleAdjustmentSolicitation solicitation) {
    var model = dayOffScheduleAdjustmentSolicitationMapper.toModel(solicitation);
    dayOffScheduleAdjustmentSolicitationDao.save(model);
  }

  @Override
  public void add(DayOffScheduleAdjustmentSolicitation solicitation) {
    var model = dayOffScheduleAdjustmentSolicitationMapper.toModel(solicitation);
    dayOffScheduleAdjustmentSolicitationDao.save(model);
  }

  @Override
  public void add(TimePunchAdjustmentSolicitation solicitation) {
    var model = timePunchAdjustmentSolicitationMapper.toModel(solicitation);
    timePunchAdjustmentSolicitationDao.save(model);
  }

  @Override
  public Pair<Array<TimePunchAdjustmentSolicitation>, PlusIntegerNumber> findManyTimePunchAdjustmentSolicitationsByCollaborationSector(
      CollaborationSector sector, PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var models = timePunchAdjustmentSolicitationDao.findAllBySenderResponsibleAccountSectorOrderByDateDesc(
        sector.value(),
        pageRequest);
    var items = models.stream().toList();
    var itemsCount = models.getTotalElements();

    return new Pair<>(
        Array.createFrom(items, timePunchAdjustmentSolicitationMapper::toEntity),
        PlusIntegerNumber.create((int) itemsCount));
  }

  @Override
  public Pair<Array<VacationSolicitation>, PlusIntegerNumber> findManyVacationSolicitationsByCollaboratorId(
      CollaborationSector sector, PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var models = vacationSolicitationDao.findAllBySenderResponsibleAccountSectorOrderByDateDesc(
        sector.value(), pageRequest);
    var items = models.stream().toList();
    var itemsCount = models.getTotalElements();
    return new Pair<>(
        Array.createFrom(items, vacationSolicitationMapper::toEntity),
        PlusIntegerNumber.create((int) itemsCount));
  }

  @Override
  public void add(VacationSolicitation vacationSolicitation) {
    var solicitationModel = vacationSolicitationMapper.toModel(vacationSolicitation);
    vacationSolicitationDao.save(solicitationModel);
  }

  @Override
  public void replace(VacationSolicitation vacationSolicitation) {
    var solicitationModel = vacationSolicitationMapper.toModel(vacationSolicitation);
    vacationSolicitationDao.save(solicitationModel);
  }

  @Override
  public Pair<Array<WithdrawSolicitation>, PlusIntegerNumber> findManyWithdrawSolicitationsByCollaborationSector(
      CollaborationSector sector, PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
    var models = withdrawSolicitationDao.findAllBySenderResponsibleAccountSectorOrderByDateDesc(
        sector.value(), pageRequest);
    var items = models.stream().toList();
    var itemsCount = models.getTotalElements();
    return new Pair<>(
        Array.createFrom(items, withdrawSolicitationMapper::toEntity),
        PlusIntegerNumber.create((int) itemsCount));
  }

  @Override
  public void add(WithdrawSolicitation solicitation) {
    var solicitationModel = withdrawSolicitationMapper.toModel(solicitation);
    withdrawSolicitationDao.save(solicitationModel);
  }

  @Override
  public void replace(WithdrawSolicitation solicitation) {
    var solicitationModel = withdrawSolicitationMapper.toModel(solicitation);
    withdrawSolicitationDao.save(solicitationModel);
  }

  @Override
  public void addJustificationToSolicitation(WithdrawSolicitation solicitation, Justification justification) {
    var solicitationModel = withdrawSolicitationMapper.toModel(solicitation);
    var justificationModel = justificationMapper.toModel(justification);
    solicitationModel.setJustification(justificationModel);
    withdrawSolicitationDao.save(solicitationModel);
  }

@Override
public Optional<WithdrawSolicitation> findWithdrawSolicitationById(Id id) {
    var solicitationModel = withdrawSolicitationDao.findById(id.value());
    if (solicitationModel.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(withdrawSolicitationMapper.toEntity(solicitationModel.get()));
}

}
