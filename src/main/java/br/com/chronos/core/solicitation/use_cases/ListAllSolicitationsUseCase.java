package br.com.chronos.core.solicitation.use_cases;

import java.util.List;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Role;
import br.com.chronos.core.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;

public class ListAllSolicitationsUseCase {
  private final SolicitationsRepository repository;

  public ListAllSolicitationsUseCase(SolicitationsRepository repository) {
    this.repository = repository;
  }

  public List<SolicitationDto> execute(CollaborationSector sector, Role role, Id userId) {
    List<SolicitationDto> dtos;
    if (role.isEmployee().isTrue()) {
      var solicitations = repository.findAllByCollaboratorId(userId);
      dtos = solicitations.map(solicitation -> solicitation.getDto()).list();
    } else {
      var solicitations = repository.findAllByCollaboratorSector(sector);
      dtos = solicitations.map(solicitation -> solicitation.getDto()).list();
    }
    return dtos;
  }

}
