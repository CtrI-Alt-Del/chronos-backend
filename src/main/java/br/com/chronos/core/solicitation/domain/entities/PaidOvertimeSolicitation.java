package br.com.chronos.core.solicitation.domain.entities;

import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.domain.dtos.PaidOvertimeSolicitationDto;

public final class PaidOvertimeSolicitation extends Solicitation {
  public PaidOvertimeSolicitation(PaidOvertimeSolicitationDto dto) {
    super(dto);
  }

  public PaidOvertimeSolicitationDto getDto() {
    return (PaidOvertimeSolicitationDto) super.getDto();
  }
}
