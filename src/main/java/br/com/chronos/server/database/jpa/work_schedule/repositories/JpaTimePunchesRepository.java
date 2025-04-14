package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.server.database.jpa.work_schedule.mappers.TimePunchMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.TimePunchModel;

interface JpaTimePunchModelsRepository extends JpaRepository<TimePunchModel, UUID> {
  @Modifying
  @Query(value = "DELETE FROM time_punches WHERE id IN (:timePunchIds)", nativeQuery = true)
  void deleteMany(@Param("timePunchIds") List<UUID> timePunchIds);

  @Modifying
  @Query(value = "DELETE FROM time_punches WHERE id IN (SELECT id FROM workday_logs WHERE date = :date)", nativeQuery = true)
  void deleteAllByWorkdayLogDate(@Param("date") LocalDate date);
}

public class JpaTimePunchesRepository implements TimePunchesRepository {
  @Autowired
  JpaTimePunchModelsRepository repository;

  @Autowired
  TimePunchMapper mapper;

  @Override
  public Optional<TimePunch> findById(Id id) {
    var timePunchModel = repository.findById(id.value());

    if (timePunchModel.isEmpty()) {
      return Optional.empty();
    }

    var timePunch = mapper.toEntity(timePunchModel.get());
    return Optional.of(timePunch);
  }

  @Override
  public void replace(TimePunch timePunch) {
    var timePunchModel = mapper.toModel(timePunch);
    repository.save(timePunchModel);
  }

  @Override
  @Transactional
  public void replaceMany(Array<TimePunch> timePunches) {
    var timePunchModels = timePunches.map(mapper::toModel);
    repository.saveAll(timePunchModels.list());
  }
}
