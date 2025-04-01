package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.WeekdaySchedule;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WeekdaySchedulesRespository;
import br.com.chronos.server.database.jpa.collaborator.models.CollaboratorModel;
import br.com.chronos.server.database.jpa.work_schedule.mappers.TimePunchMapper;
import br.com.chronos.server.database.jpa.work_schedule.mappers.WeekdayScheduleMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.TimePunchModel;
import br.com.chronos.server.database.jpa.work_schedule.models.WeekdayScheduleModel;

interface JpaWeekdayScheduleModelsRepository extends JpaRepository<WeekdayScheduleModel, UUID> {
  @Modifying
  @Query(value = "DELETE FROM weekday_schedules WHERE work_schedule_id = :workScheduleId", nativeQuery = true)
  void deleteManyByWorkSchedule(@Param("workScheduleId") UUID workScheduleId);

  @Query(value = "SELECT EXISTS (SELECT 1 FROM weekday_schedules WHERE time_punch_id = :timePunchId)", nativeQuery = true)
  boolean timePunchLogExists(@Param("timePunchId") UUID timePunchId);

  List<WeekdayScheduleModel> findManyByCollaborator(CollaboratorModel collaboratorModel);
}

public class JpaWeekdaySchedulesRepository implements WeekdaySchedulesRespository {
  @Autowired
  private JpaWeekdayScheduleModelsRepository weekdayScheduleModelsRepository;

  @Autowired
  JpaTimePunchModelsRepository timePunchModelsRepository;

  @Autowired
  private WeekdayScheduleMapper weekdayScheduleMapper;

  @Autowired
  private TimePunchMapper timePunchMapper;

  @Override
  public Array<WeekdaySchedule> findManyByCollaborator(Id collaborator) {
    var collaboratorModel = CollaboratorModel.builder().id(collaborator.value()).build();
    var weekdayScheduleModels = weekdayScheduleModelsRepository.findManyByCollaborator(collaboratorModel);
    return Array.createFrom(weekdayScheduleModels, weekdayScheduleMapper::toEntity);
  }

  @Override
  public void addMany(Array<WeekdaySchedule> weekdaysSchedules, Id collaborator) {
    addWeekSchedule(weekdaysSchedules, collaborator);
  }

  private void addWeekSchedule(Array<WeekdaySchedule> weekdaySchedules, Id collaborator) {
    var collaboratorModel = CollaboratorModel.builder().id(collaborator.value()).build();
    Array<TimePunchModel> timePunchModels = Array.createAsEmpty();

    var weekdayScheduleModels = weekdaySchedules.map((weekdaySchedule) -> {
      var weekdayScheduleModel = weekdayScheduleMapper.toModel(weekdaySchedule);
      var timePunchModel = timePunchMapper.toModel(weekdaySchedule.getTimePunch());
      timePunchModels.add(timePunchModel);
      weekdayScheduleModel.setCollaborator(collaboratorModel);
      weekdayScheduleModel.setTimePunch(timePunchModel);
      return weekdayScheduleModel;
    });
    timePunchModelsRepository.saveAll(timePunchModels.list());
    weekdayScheduleModelsRepository.saveAll(weekdayScheduleModels.list());
  }

  @Override
  public void replaceMany(Array<WeekdaySchedule> weekdaysSchedule, Id collaborator) {
    throw new UnsupportedOperationException("Unimplemented method 'replaceMany'");
  }

}
