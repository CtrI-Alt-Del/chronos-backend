package br.com.chronos.server.database.jpa.portal.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.portal.domain.entities.DayOffSolicitation;
import br.com.chronos.core.portal.interfaces.repositories.DayOffSolicitationRepository;
import br.com.chronos.server.database.jpa.portal.mappers.DayOffSolicitationMapper;
import br.com.chronos.server.database.jpa.portal.models.DayOffSolicitationModel;

interface JpaDayOffSolicitationModelRepository extends JpaRepository<DayOffSolicitationModel,UUID>{}
public class JpaDayOffSolicitationRepository implements DayOffSolicitationRepository{
  

  @Autowired
  private JpaDayOffSolicitationModelRepository solicitationRepository;

  @Autowired
  private DayOffSolicitationMapper mapper;

	@Override
	public void add(DayOffSolicitation solicitation) {
    var solicitationModel = mapper.toModel(solicitation);
    solicitationRepository.save(solicitationModel);
	}

	@Override
	public void resolveSolicitation(DayOffSolicitation solicitation) {
    var solicitationModel = mapper.toModel(solicitation);
    solicitationModel.setSolicitationStatus(solicitation.getStatus().value());
    solicitationRepository.save(solicitationModel);
    return;
	}

	@Override
	public Optional<DayOffSolicitation> findSolicitationById(Id solicitationId) {
    var solicitationModel = solicitationRepository.findById(solicitationId.value());
    if (solicitationModel.isEmpty()) {
      return Optional.empty();
    }
    var solicitation = mapper.toEntity(solicitationModel.get());
    return Optional.of(solicitation);
	}

}
