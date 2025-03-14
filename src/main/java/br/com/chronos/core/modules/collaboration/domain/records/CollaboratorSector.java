package br.com.chronos.core.modules.collaboration.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
import br.com.chronos.core.modules.global.domain.records.Text;

public record CollaboratorSector(Sector value) {
  public enum Sector {
    PRODUCTION,
    COMERCIAL,
    ADMINISTRATIVE
  }

  public static CollaboratorSector create(String sector) {
    if (sector == null) {
      return new CollaboratorSector(Sector.PRODUCTION);
    }
    var text = Text.create(sector.toUpperCase(), "collaborator sector");
    if (text.notEqualsTo(Sector.PRODUCTION.toString()).value()
        && text.notEqualsTo(Sector.COMERCIAL.toString()).value()
        && text.notEqualsTo(Sector.ADMINISTRATIVE.toString()).value()) {
      throw new ValidationException("collaborator sector", "must be from production,comercial or administrative");
    }
    return new CollaboratorSector(Sector.valueOf(sector));
  }

  @Override
  public String toString() {
    return value.toString().toLowerCase();
  }

  public Boolean isFromProduction() {
    return value == Sector.PRODUCTION;
  }

  public Boolean isFromComercial() {
    return value == Sector.COMERCIAL;
  }

  public Boolean isFromAdministrative() {
    return value == Sector.ADMINISTRATIVE;
  }
}
