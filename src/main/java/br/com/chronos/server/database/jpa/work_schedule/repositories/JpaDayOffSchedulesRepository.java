package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.entities.DayOffSchedule;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.daos.DayOffDao;
import br.com.chronos.server.database.jpa.work_schedule.daos.DayOffScheduleDao;
import br.com.chronos.server.database.jpa.work_schedule.mappers.DayOffMapper;
import br.com.chronos.server.database.jpa.work_schedule.mappers.DayOffScheduleMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.DayOffModel;

public class JpaDayOffSchedulesRepository implements DayOffSchedulesRepository {
  @Autowired
  private DayOffScheduleDao dayOffScheduleDao;

  @Autowired
  private DayOffDao dayOffDao;

  @Autowired
  private DayOffScheduleMapper dayOffScheduleMapper;

  @Autowired
  private DayOffMapper dayOffMapper;

  @Override
  public Array<DayOffSchedule> findAll() {
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public Optional<DayOffSchedule> findByCollaborator(Id collaborator) {
    var collaboratorModel = CollaboratorModel.builder().id(collaborator.value()).build();
    var dayOffScheduleModel = dayOffScheduleDao.findByCollaborator(collaboratorModel);
    if (dayOffScheduleModel.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(dayOffScheduleMapper.toEntity(dayOffScheduleModel.get()));
  }

  @Override
  @Transactional
  public void add(DayOffSchedule dayOffSchedule, Id collaboratorId) {
    var collaboratorModel = CollaboratorModel.builder().id(collaboratorId.value()).build();
    var dayOffScheduleModel = dayOffScheduleMapper.toModel(dayOffSchedule);
    dayOffScheduleModel.setCollaborator(collaboratorModel);

    var dayOffModels = dayOffSchedule.getDaysOff().map((dayOff) -> {
      var dayOffModel = dayOffMapper.toModel(dayOff);
      dayOffModel.setDayOffSchedule(dayOffScheduleModel);
      return dayOffModel;
    });

    dayOffScheduleDao.save(dayOffScheduleModel);
    dayOffDao.saveAll(dayOffModels.list());
  }

  @Override
  @Transactional
  public void addMany(Array<DayOffSchedule> dayOffSchedules, Id collaboratorId) {
    var collaboratorModel = CollaboratorModel.builder().id(collaboratorId.value()).build();
    Array<DayOffModel> allDayOffModels = Array.createAsEmpty();

    var dayOffScheduleModels = dayOffSchedules.map((dayOffSchedule) -> {
      var dayOffScheduleModel = dayOffScheduleMapper.toModel(dayOffSchedule);
      dayOffScheduleModel.setCollaborator(collaboratorModel);

      var dayOffModels = dayOffSchedule.getDaysOff().map((dayOff) -> {
        var dayOffModel = dayOffMapper.toModel(dayOff);
        dayOffModel.setDayOffSchedule(dayOffScheduleModel);
        return dayOffModel;
      });

      allDayOffModels.addArray(dayOffModels);
      return dayOffScheduleModel;
    });
    dayOffScheduleDao.saveAll(dayOffScheduleModels.list());
    dayOffDao.saveAll(allDayOffModels.list());
  }

  @Override
  @Transactional
  public void replace(DayOffSchedule dayOffSchedule, Id collaboratorId) {
    var collaboratorModel = CollaboratorModel.builder().id(collaboratorId.value()).build();
    var dayOffScheduleModel = dayOffScheduleDao.findByCollaborator(collaboratorModel);
    dayOffDao.deleteManyByDayOffSchedule(dayOffScheduleModel.get().getId());

    var dayOffModels = dayOffSchedule.getDaysOff().map((dayOff) -> {
      var dayOffModel = dayOffMapper.toModel(dayOff);
      dayOffModel.setDayOffSchedule(dayOffScheduleModel.get());
      return dayOffModel;
    });
    dayOffDao.saveAll(dayOffModels.list());
  }

  @Override
  @Transactional
  public void replaceMany(Array<DayOffSchedule> dayOffSchedules) {
    var dayOffScheduleModels = dayOffSchedules.map((dayOffSchedule) -> {
      var dayOffScheduleModel = dayOffScheduleMapper.toModel(dayOffSchedule);
      dayOffDao.deleteManyByDayOffSchedule(dayOffScheduleModel.getId());
      return dayOffScheduleModel;
    });

    dayOffScheduleDao.saveAll(dayOffScheduleModels.list());
  }
}
