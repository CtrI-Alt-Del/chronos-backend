package br.com.chronos.core.global.domain.aggregates;

import br.com.chronos.core.global.domain.abstracts.Aggregate;
import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.dtos.ResponsibleDto;
import br.com.chronos.core.global.domain.entities.Responsible;

public class ResponsibleAggregate extends Aggregate<Responsible> {
  private static String ENTITY_NAME = "Responsável";

  public ResponsibleAggregate(ResponsibleAggregateDto aggregateDto) {
    super(
        aggregateDto.id,
        (aggregateDto.dto != null) ? new Responsible(aggregateDto.dto) : null,
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
                .setId(responsibleDto.id)
                .setName(responsibleDto.name)
                .setEmail(responsibleDto.email)
                .setCpf(responsibleDto.cpf)
                .setSector(responsibleDto.sector)
                .setRole(responsibleDto.role));
  }
}
