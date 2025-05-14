package br.com.chronos.core.portal.interfaces;

import br.com.chronos.core.portal.domain.events.DayOffScheduleSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.SolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.SolicitationCreatedEvent;
import br.com.chronos.core.portal.domain.events.SolicitationDeniedEvent;
import br.com.chronos.core.portal.domain.events.TimePunchAdjusmentSolicitationApprovedEvent;

public interface PortalBroker {
  void publish(SolicitationApprovedEvent event);

  void publish(SolicitationCreatedEvent event);

  void publish(SolicitationDeniedEvent event);

  void publish(ExcusedAbsenceSolicitationApprovedEvent event);

  void publish(DayOffSolicitationApprovedEvent event);

  void publish(DayOffScheduleSolicitationApprovedEvent event);

  void publish(TimePunchAdjusmentSolicitationApprovedEvent event);
}
