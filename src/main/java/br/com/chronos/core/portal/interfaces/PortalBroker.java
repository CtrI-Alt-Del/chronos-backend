package br.com.chronos.core.portal.interfaces;

import br.com.chronos.core.portal.domain.events.DayOffScheduleSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.DayOffSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.ExcusedAbsenceSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.TimePunchAdjusmentSolicitationApprovedEvent;
import br.com.chronos.core.portal.domain.events.VacationSolicitationApprovedEvent;

public interface PortalBroker {
  void publish(ExcusedAbsenceSolicitationApprovedEvent event);

  void publish(DayOffSolicitationApprovedEvent event);

  void publish(DayOffScheduleSolicitationApprovedEvent event);

  void publish(TimePunchAdjusmentSolicitationApprovedEvent event);

  void publish(VacationSolicitationApprovedEvent event);
}
