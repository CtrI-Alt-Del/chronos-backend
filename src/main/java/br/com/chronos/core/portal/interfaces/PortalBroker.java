package br.com.chronos.core.portal.interfaces;

import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.ExcusedAbsenceSolicitationApprovedEvent;

public interface PortalBroker {
  void publish(ExcusedAbsenceSolicitationApprovedEvent event);

  void publish(DayOffSolicitationApprovedEvent event);
}
