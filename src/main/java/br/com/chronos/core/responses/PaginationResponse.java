package br.com.chronos.core.responses;

import java.util.List;

public class PaginationResponse<Item> {
  private final List<Item> items;
  private final int itemsCount;

  public PaginationResponse(List<Item> items, int itemsCount) {
    this.items = items;
    this.itemsCount = itemsCount;
  }

  public List<Item> getItems() {
    return items;
  }

  public int getItemsCount() {
    return itemsCount;
  }
}
