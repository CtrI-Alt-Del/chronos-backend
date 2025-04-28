package br.com.chronos.core.solicitation.interfaces;

import br.com.chronos.core.solicitation.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.solicitation.domain.events.PaidOvertimeSolicitationApprovedEvent;

public interface PortalBroker {
  void publish(PaidOvertimeSolicitationApprovedEvent event);

  void publish(ExcusedAbsenceSolicitationApprovedEvent event);
}
