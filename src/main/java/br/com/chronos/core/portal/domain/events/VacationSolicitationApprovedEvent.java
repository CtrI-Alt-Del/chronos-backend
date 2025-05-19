package br.com.chronos.core.portal.domain.events;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.portal.domain.entities.VacationSolicitation;

public class VacationSolicitationApprovedEvent extends Event<VacationSolicitationApprovedEvent.Payload> {
    public static final String NAME = "portal/vacation.solicitation.approved";

    public static record Payload(String collaboratorId, String vacation) {
    }

    public VacationSolicitationApprovedEvent(VacationSolicitation solicitation) {
        super(new Payload(
            solicitation.getSenderResponsible().getId().toString(),
            solicitation.getVacationDays().firstItem().value() + " - " +
            solicitation.getVacationDays().lastItem().value() + " (" +
            solicitation.getVacationDays().size().value()
        ));
    }
    
}
