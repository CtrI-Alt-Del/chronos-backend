package br.com.chronos.core.global.domain.records;

public record Count(PlusIntegerNumber integer) {
  public static Count create(int value, String key) {
    var integer = PlusIntegerNumber.create(value, key);
    return new Count(integer);
  }

  public static Count create() {
    var integer = PlusIntegerNumber.create(0);
    return new Count(integer);
  }

  public Count increment() {
    return new Count(integer.plus(1));
  }

  public Logical isEqual(Count count) {
    return integer.isEqual(count.integer);
  }

  public Logical isEqual(int countIntegerValue) {
    return integer.isEqual(PlusIntegerNumber.create(countIntegerValue));
  }

  public Count reset() {
    return Count.create();
  }
}
