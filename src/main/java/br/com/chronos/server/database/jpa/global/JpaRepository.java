package br.com.chronos.server.database.jpa.global;

import org.springframework.data.domain.PageRequest;

import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.responses.PaginationResponse;

public class JpaRepository {
  public static PageRequest getPageRequest(PageNumber page) {
    return PageRequest.of(page.number().value() - 1, PaginationResponse.ITEMS_PER_PAGE);
  }
}
