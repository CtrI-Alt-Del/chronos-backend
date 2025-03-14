package br.com.chronos.server.database.jpa.work_schedule.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.server.database.jpa.work_schedule.mappers.TimePunchMapper;
import br.com.chronos.server.database.jpa.work_schedule.models.TimePunchModel;

interface JpaTimePunchModelsRepository extends JpaRepository<TimePunchModel, UUID> {

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
  public void update(TimePunch timePunch) {
    var timePunchModel = mapper.toModel(timePunch);
    repository.save(timePunchModel);
  }
}
