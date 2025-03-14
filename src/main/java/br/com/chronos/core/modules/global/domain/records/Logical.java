package br.com.chronos.core.modules.global.domain.records;

public record Logical(boolean value) {
  public static Logical create(boolean value) {
    return new Logical(value);
  }

  public boolean isTrue() {
    return value == true;
  }

  public boolean isFalse() {
    return value == false;
  }

  public boolean and(Logical logical) {
    return isTrue() && logical.isTrue();
  }

  public boolean or(Logical logical) {
    return isTrue() || logical.isTrue();
  }
}
