package br.com.chronos.core.modules.global.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;

public record CollaborationSector(Sector value) {
  public enum Sector {
    PRODUCTION,
    COMERCIAL,
    ADMINISTRATIVE,
    HUMAN_RESOURCES
  }

  public static CollaborationSector create(String sector) {
    if (sector == null) {
      return new CollaborationSector(Sector.PRODUCTION);
    }
    var text = Text.create(sector.toUpperCase(), "setor");
    try {
      return new CollaborationSector(Sector.valueOf(text.value()));
    } catch (Exception e) {
      throw new ValidationException(text.key(), "deve ser do setor de produção, comercial ou administrativo");
    }
  }

  public String toString() {
    return value.toString().toLowerCase();
  }

  public Logical isFromProduction() {
    return Logical.create(value == Sector.PRODUCTION);
  }

  public Logical isFromComercial() {
    return Logical.create(value == Sector.COMERCIAL);
  }

  public Logical isFromAdministrative() {
    return Logical.create(value == Sector.ADMINISTRATIVE);
  }

  public Logical isFromHumanResources() {
    return Logical.create(value == Sector.HUMAN_RESOURCES);
  }
}
