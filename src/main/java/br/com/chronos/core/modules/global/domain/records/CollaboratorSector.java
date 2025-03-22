package br.com.chronos.core.modules.global.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;

public record CollaboratorSector(Sector value) {
  public enum Sector {
    PRODUCTION,
    COMERCIAL,
    ADMINISTRATIVE,
    HUMAN_RESOURCES
  }

  public static CollaboratorSector create(String sector) {
    if (sector == null) {
      return new CollaboratorSector(Sector.PRODUCTION);
    }
    var text = Text.create(sector.toUpperCase(), "setor");
    try {
      return new CollaboratorSector(Sector.valueOf(text.value()));
    } catch (Exception e) {
      throw new ValidationException(text.key(), "deve ser do setor produtivo, comercial ou administrativo");
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
