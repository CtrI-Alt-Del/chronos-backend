package br.com.chronos.core.modules.global.domain.records;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record Array<Item>(List<Item> items) {

  public static <Item> Array<Item> create(List<Item> items) {
    return new Array<Item>(items);
  }

  public static <Item, NewItem> Array<NewItem> createFrom(
      List<Item> items, Function<Item, NewItem> mapper) {

    var list = items.stream().map(mapper).collect(Collectors.toList());

    return new Array<>(list);
  }

  public Array<Item> add(Item item) {
    items.add(item);
    return new Array<>(items);
  }

  public Array<Item> remove(Item item) {
    items.remove(item);
    return new Array<>(items);
  }

  public <NewItem> Array<NewItem> map(Function<Item, NewItem> mapper) {
    var list = items
        .stream()
        .map(mapper)
        .collect(Collectors.toList());
    return new Array<>(list);
  }

  public Array<Item> filter(Predicate<? super Item> predicate) {
    var list = items
        .stream()
        .filter(predicate)
        .collect(Collectors.toList());
    return new Array<>(list);
  }

  public int size() {
    return items.size();
  }
}
