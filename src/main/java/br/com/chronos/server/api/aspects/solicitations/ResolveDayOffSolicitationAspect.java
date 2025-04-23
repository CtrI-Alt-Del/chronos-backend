package br.com.chronos.server.api.aspects.solicitations;

import java.time.LocalDate;
import java.time.LocalTime;

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
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.hour_bank.use_cases.CalculateHourBankBalanceUseCase;
import br.com.chronos.core.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.solicitation.interfaces.repositories.SolicitationsRepository;
import br.com.chronos.server.api.controllers.solicitation.solicitations.ResolveSolicitationController.ResolveSolicitationRequestBody;

@Component
@Aspect
public class ResolveDayOffSolicitationAspect {


  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  @Pointcut("execution(* br.com.chronos.server.api.controllers.solicitation.solicitations.ResolveSolicitationController.handle(..))")
  public void handleResolveSolicitation() {
  }

  @Before("handleResolveSolicitation() && args(solicitationId, body,..)")
  public void validateHourBankBeforeResolve(String solicitationId, ResolveSolicitationRequestBody body) {
    boolean isDayOff = "DAY_OFF".equals(body.getSolicitationType());
    boolean isApproved = "APPROVED".equalsIgnoreCase(body.getStatus());

    if (!isDayOff || !isApproved) {
      return; 
    }

    var solicitation = getSolicitation(Id.create(solicitationId));
    var collaboratorId = solicitation.getSenderResponsible().getId();
    var hourBankBalance = getHourBankBalance(collaboratorId);
    var collaborator = getCollaborator(collaboratorId);

    var requiredHours = (int) collaborator.getWorkload().value();
    if (hourBankBalance < requiredHours) {
      throw new ValidationException("Banco de horas",
          "Esse funcionario nao tem horas insuficientes para tirar um dia de folga");
    }
    registerHourBankDebitTransaction(collaboratorId, requiredHours);
  }

  private Solicitation getSolicitation(Id solicitationId) {
    var solicitation = solicitationsRepository.findSolicitationById(solicitationId);
    if (solicitation.isEmpty()) {
      throw new NotFoundException("Solicitação não encontrada");
    }
    return solicitation.get();
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

  private void registerHourBankDebitTransaction(Id collaboratorId, int hours) {
    var hourBankTransactionDto = new HourBankTransactionDto()
        .setReason("ADJUSTMENT")
        .setOperation("DEBIT")
        .setTime(LocalTime.of(hours, 0))
        .setDate(LocalDate.now());

    var hourBankTranscation = HourBankTransaction.create(hourBankTransactionDto);

    hourBankTransactionsRepository.add(hourBankTranscation, collaboratorId);
  }
}
