package br.com.chronos.core.global.responses;

import java.util.List;

public class PaginationResponse<Item> {
  private final List<Item> items;
  private final int itemsCount;
  private final int pagesCount;
  public final static int ITEMS_PER_PAGE = 10;

  public PaginationResponse(List<Item> items, int itemsCount) {
    var pagesCount = (int) Math.ceil(itemsCount / (double) ITEMS_PER_PAGE);
    this.items = items;
    this.itemsCount = itemsCount;
    this.pagesCount = (pagesCount > 1) ? pagesCount : 0;
  }

  public List<Item> getItems() {
    return items;
  }

  public int getItemsCount() {
    return itemsCount;
  }

  public int getPagesCount() {
    return pagesCount;
  }
}
