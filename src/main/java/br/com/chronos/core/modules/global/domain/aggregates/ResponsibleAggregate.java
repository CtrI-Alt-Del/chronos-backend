package br.com.chronos.core.modules.global.domain.aggregates;

import br.com.chronos.core.modules.global.domain.abstracts.Aggregate;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.modules.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.modules.global.domain.entities.Responsible;

public class ResponsibleAggregate extends Aggregate<Responsible> {
  private static String ENTITY_NAME = "Responsável";

  public ResponsibleAggregate(ResponsibleAggregateDto aggregateDto) {
    super(
        aggregateDto.id,
        (aggregateDto.responsibleDto != null) ? new Responsible(aggregateDto.responsibleDto) : null,
        ENTITY_NAME);
  }

  public ResponsibleAggregateDto getDto() {
    if (isEntityNull().isTrue()) {
      return new ResponsibleAggregateDto().setId(getId().toString());
    }

    var responsibleDto = (getEntity() != null) ? getEntity().getDto() : null;
    return new ResponsibleAggregateDto()
        .setId(getId().toString())
        .setResponsibleDto(
            new ResponsibleDto()
                .setName(responsibleDto.name)
                .setEmail(responsibleDto.email)
                .setRole(responsibleDto.role));
  }
}
