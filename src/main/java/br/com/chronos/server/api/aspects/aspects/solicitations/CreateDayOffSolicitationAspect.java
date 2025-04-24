package br.com.chronos.server.api.aspects.aspects.solicitations;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.CalculateHourBankBalanceUseCase;
import br.com.chronos.core.solicitation.domain.dtos.DayOffSolicitationDto;

@Component
@Aspect
public class CreateDayOffSolicitationAspect {

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  @Pointcut("execution(* br.com.chronos.server.api.controllers.solicitation.solicitations.CreateDayOffSolicitationController.handle(..))")
  public void handleCreateDayOffSolicitation() {
  }

  @Before("handleCreateDayOffSolicitation() && args(body,..)")
  private void validateHourBankBeforeCreate(DayOffSolicitationDto body) {
    var collaboratorId = authenticationProvider.getAccount().getCollaboratorId();
    var collaborator = getCollaborator(collaboratorId);
    var hourBankBalance = getHourBankBalance(collaboratorId);
    var requiredHours = (int) collaborator.getWorkload().value();
    if (hourBankBalance < requiredHours) {
      throw new ValidationException("Banco de horas",
          "Voce nao tem horas suficientes no banco de horas para solicitar uma folga");
    }
    return;
  }

  private Collaborator getCollaborator(Id collaboratorId) {
    var collaborator = collaboratorsRepository.findById(collaboratorId);
    if (collaborator.isEmpty()) {
      throw new NotFoundException("Colaborador não encontrado");
    }
    return collaborator.get();
  }

  private int getHourBankBalance(Id collaboratorId) {
    var useCase = new CalculateHourBankBalanceUseCase(hourBankTransactionsRepository);
    return useCase.execute(collaboratorId.value().toString()).value.getHour();
  }

}
