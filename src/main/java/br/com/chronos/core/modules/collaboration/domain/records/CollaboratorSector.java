package br.com.chronos.core.modules.collaboration.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
import br.com.chronos.core.modules.global.domain.records.Logical;
import br.com.chronos.core.modules.global.domain.records.Text;

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
    var text = Text.create(sector.toUpperCase(), "collaborator sector");
    try {
      return new CollaboratorSector(Sector.valueOf(text.value()));
    } catch (Exception e) {
      throw new ValidationException(text.key(), "must be from production,comercial or administrative");
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
  public Logical isFromHumanResources(){
    return Logical.create(value == Sector.HUMAN_RESOURCES);
  }
}
