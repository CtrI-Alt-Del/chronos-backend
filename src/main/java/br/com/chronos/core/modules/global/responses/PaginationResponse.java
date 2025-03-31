package br.com.chronos.core.modules.global.responses;

import java.util.List;

public class PaginationResponse<Item> {
  private final List<Item> items;
  private final int itemsCount;
  private final int pagesCount;
  public final static int ITEMS_PER_PAGE = 10;

  public PaginationResponse(List<Item> items, int itemsCount) {
    this.items = items;
    this.itemsCount = itemsCount;
    this.pagesCount = (int) Math.ceil(itemsCount / ITEMS_PER_PAGE);
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
