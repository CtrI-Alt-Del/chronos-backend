package br.com.chronos.server.database.jpa.solicitation.repositories;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
import br.com.chronos.server.database.jpa.solicitation.mappers.TimePunchLogAdjustmentSolicitationMapper;
import br.com.chronos.server.database.jpa.solicitation.models.TimePunchLogAdjustmentSolicitationModel;

interface JpaTimePunchLogAdjustmentSolicitationModelsRepository
    extends JpaRepository<TimePunchLogAdjustmentSolicitationModel, UUID> {
}
public class JpaSolicitationsRepository implements SolicitationsRepository{

  @Autowired
  JpaTimePunchLogAdjustmentSolicitationModelsRepository timePunchLogAdjustmentSolicitationModelsRepository;

  @Autowired
  TimePunchLogAdjustmentSolicitationMapper mapper;

	@Override
	public void addTimePunchLogAdjustmentSolicitation(TimePunchLogAdjustmentSolicitation solicitation) {
    var timePunchAdjustmentSolicitationModel = mapper.toModel(solicitation);
    timePunchLogAdjustmentSolicitationModelsRepository.save(timePunchAdjustmentSolicitationModel);
	}



}
