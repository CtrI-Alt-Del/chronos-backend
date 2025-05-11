package br.com.chronos.core.portal.interfaces;

import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.PaidOvertimeSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.TimePunchAdjusmentSolicitationApprovedEvent;

public interface PortalBroker {
  void publish(ExcusedAbsenceSolicitationApprovedEvent event);

  void publish(DayOffSolicitationApprovedEvent event);

  void publish(TimePunchAdjusmentSolicitationApprovedEvent event);
}
