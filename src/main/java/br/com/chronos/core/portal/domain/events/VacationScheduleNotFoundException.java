package br.com.chronos.core.portal.domain.events;

import br.com.chronos.core.global.domain.exceptions.NotFoundException;

public class VacationScheduleNotFoundException extends NotFoundException {
    public VacationScheduleNotFoundException() {
        super("Escala de férias não encontrada");
    }

}
