package br.com.chronos.core.modules.solicitation.domain.entities;

import java.time.LocalTime;

import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.dtos.SolicitationDto;
import br.com.chronos.core.modules.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.modules.work_schedule.domain.records.TimePunchPeriod;

public final class TimePunchLogAdjustmentSolicitation extends Solicitation {
    private LocalTime time;
    private TimePunchPeriod period;

    private TimePunchLogAdjustmentSolicitation(TimePunchLogAdjustmentSolicitationDto dto) {
        super(dto);
        time = dto.time;
        period = TimePunchPeriod.create(dto.period);
    }

    public LocalTime getTime() {
        return time;
    }

    public TimePunchPeriod getPeriod() {
        return period;
    }

    public SolicitationDto getDto() {
    TimePunchLogAdjustmentSolicitationDto dto = new TimePunchLogAdjustmentSolicitationDto();
    dto.setId(getId().toString());
    dto.setTime(getTime());
    dto.setPeriod(getPeriod().toString());
    return dto;
}

 
}
