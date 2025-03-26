package br.com.chronos.core.modules.solicitation.domain.entities;

import br.com.chronos.core.modules.global.domain.records.Time;
import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.modules.work_schedule.domain.records.TimePunchPeriod;

public final class TimePunchLogAdjustmentSolicitation extends Solicitation {
    private Time time;
    private TimePunchPeriod period;

    public TimePunchLogAdjustmentSolicitation(TimePunchLogAdjustmentSolicitationDto dto) {
        super(dto);
        time = new Time(dto.time);
        period = TimePunchPeriod.create(dto.period);
    }

    public Time getTime() {
        return time;
    }

    public TimePunchPeriod getPeriod() {
        return period;
    }

    public SolicitationDto getDto() {
    TimePunchLogAdjustmentSolicitationDto dto = new TimePunchLogAdjustmentSolicitationDto();
    dto.setId(getId().toString());
    dto.setTime(getTime().value());
    dto.setPeriod(getPeriod().toString());
    return dto;
}

 
}
