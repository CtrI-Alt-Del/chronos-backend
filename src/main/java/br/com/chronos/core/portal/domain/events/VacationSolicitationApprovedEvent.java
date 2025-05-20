package br.com.chronos.core.portal.domain.events;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.portal.domain.entities.VacationSolicitation;

public class VacationSolicitationApprovedEvent extends Event<VacationSolicitationApprovedEvent.Payload> {
    public static final String NAME = "portal/vacation.solicitation.approved";

    public static record Payload(String collaboratorId, String startDate, String endDate) {

        public static Payload fromVacationSolicitation (VacationSolicitation Solicitation) {
        return new Payload(
                Solicitation.getSenderResponsible().getId().toString(),
                Solicitation.getStartDate().toString(),
                Solicitation.getEndDate().toString()
        );
    }
    }

    public VacationSolicitationApprovedEvent(VacationSolicitation solicitation) {
        super(Payload.fromVacationSolicitation(solicitation));
    }
  
}

