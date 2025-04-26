package br.com.chronos.server.api.controllers.solicitation.solicitations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chronos.core.solicitation.domain.dtos.PaidOvertimeSolicitationDto;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SolicitationsController
public class CreatePaidOvertimeSolitationController {
  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class Request extends PaidOvertimeSolicitationDto {
    private int workload;
  }

  @PostMapping("/paid-overtime")
  public ResponseEntity<Void> handle(@RequestBody Request body) {
    // var useCase = new
    // CreatePaidOvertimeSolicitationUseCase(solicitationsRepository);
    // useCase.execute(body);
    System.out.println("controller");
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
