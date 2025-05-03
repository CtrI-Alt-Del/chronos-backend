package br.com.chronos.core.portal.interfaces;

import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.PaidOvertimeSolicitationApprovedEvent;

public interface PortalBroker {
  void publish(PaidOvertimeSolicitationApprovedEvent event);

  void publish(ExcusedAbsenceSolicitationApprovedEvent event);

  void publish(DayOffSolicitationApprovedEvent event);
}
