package br.com.chronos.core.modules.global.domain.records;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record Array<Item>(List<Item> list) {

  public static <Item> Array<Item> create(List<Item> items) {
    return new Array<Item>(items);
  }

  public static <Item, NewItem> Array<NewItem> createFrom(
      List<Item> items, Function<Item, NewItem> mapper) {

    var list = items.stream().map(mapper).collect(Collectors.toList());

    return new Array<>(list);
  }

  public static <Item> Array<Item> createAsEmpty() {
    return new Array<Item>(new ArrayList<>());
  }

  public Array<Item> add(Item item) {
    list.add(item);
    return new Array<>(list);
  }

  public Array<Item> remove(Item item) {
    list.remove(item);
    return new Array<>(list);
  }

  public <NewItem> Array<NewItem> map(Function<Item, NewItem> mapper) {
    var new_list = list
        .stream()
        .map(mapper)
        .collect(Collectors.toList());
    return new Array<>(new_list);
  }

  public Array<Item> filter(Predicate<? super Item> predicate) {
    var new_list = list
        .stream()
        .filter(predicate)
        .collect(Collectors.toList());
    return new Array<>(new_list);
  }

  public Logical some(Predicate<? super Item> predicate) {
    return Logical.create(list.stream().anyMatch(predicate));
  }

  public int size() {
    return list.size();
  }
}
