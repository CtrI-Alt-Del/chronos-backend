package br.com.chronos.core.modules.global.domain.abstracts;

import br.com.chronos.core.modules.global.domain.exceptions.NullEntityException;
import br.com.chronos.core.modules.global.domain.records.Logical;

public abstract class Aggregate<AggregableEntity> extends Entity {
  private AggregableEntity entity;
  private String entityName;

  public Aggregate(String id, AggregableEntity entity, String entityName) {
    super(id);
    this.entity = entity;
  }

  public AggregableEntity getEntity() {
    if (isEntityNull().isTrue()) {
      throw new NullEntityException(entityName);
    }
    return entity;
  }

  public Logical isEntityNull() {
    return Logical.create(entity == null);
  }
}
