package br.com.chronos.core.modules.solicitation.use_cases;

import java.util.List;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Role;
import br.com.chronos.core.modules.global.domain.records.CollaborationSector.Sector;
import br.com.chronos.core.modules.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.modules.solicitation.interfaces.repository.SolicitationsRepository;
public class ListAllSolicitationsUseCase {
  private final SolicitationsRepository repository;

  public ListAllSolicitationsUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public List<SolicitationDto> execute(Sector sector,Role role,Id userId) {
    var solicitationArray = repository.findMany(sector,role,userId);
    var dtos = solicitationArray.map(solicitation -> solicitation.getDto()).list();
    return dtos;
  }

}
