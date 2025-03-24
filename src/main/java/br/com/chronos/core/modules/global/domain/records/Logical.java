package br.com.chronos.core.modules.global.domain.records;

public record Logical(boolean value) {
  public static Logical create(boolean value) {
    return new Logical(value);
  }

  public static Logical createAsTrue() {
    return new Logical(true);
  }

  public static Logical createAsFalse() {
    return new Logical(false);
  }

  public boolean isTrue() {
    return value == true;
  }

  public boolean isFalse() {
    return value == false;
  }

  public Logical and(Logical logical) {
    return Logical.create(isTrue() && logical.isTrue());
  }

  public Logical andNot(Logical logical) {
    return Logical.create(isFalse() && logical.isFalse());
  }

  public Logical or(Logical logical) {
    return Logical.create(isTrue() || logical.isTrue());
  }
}
