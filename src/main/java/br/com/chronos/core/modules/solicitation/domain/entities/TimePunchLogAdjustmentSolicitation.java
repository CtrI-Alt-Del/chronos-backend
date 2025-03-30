package br.com.chronos.core.modules.solicitation.domain.entities;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Time;
import br.com.chronos.core.modules.solicitation.domain.abstracts.Solicitation;
import br.com.chronos.core.modules.solicitation.domain.dtos.TimePunchLogAdjustmentSolicitationDto;
import br.com.chronos.core.modules.work_schedule.domain.records.TimePunchPeriod;

public final class TimePunchLogAdjustmentSolicitation extends Solicitation {
    private Time time;
    private TimePunchPeriod period;
    private Id workdayLogId;

    public TimePunchLogAdjustmentSolicitation(TimePunchLogAdjustmentSolicitationDto dto) {
        super(dto);
        time = new Time(dto.time);
        period = TimePunchPeriod.create(dto.period);
        workdayLogId = Id.create(dto.workdayLogId);
    }

    public Time getTime() {
        return time;
    }

    public Id getWorkdayLogId() {
        return workdayLogId;
    }

    public TimePunchPeriod getPeriod() {
        return period;
    }

    public TimePunchLogAdjustmentSolicitationDto getDto() {
    TimePunchLogAdjustmentSolicitationDto dto = new TimePunchLogAdjustmentSolicitationDto();
    dto.setId(getId().toString());
    dto.setDescription(getDescription().value());
    dto.setDate(getDate().value());
    dto.setStatus(getStatus().value().toString());
    dto.setFeedbackMessage(getFeedbackMessage().value());
    dto.setSenderResponsible(getSenderResponsible().getDto());
    dto.setReplierResponsible(getReplierResponsible() != null ? getReplierResponsible().getDto() : null);
    dto.setTime(getTime().value());
    dto.setPeriod(getPeriod().toString());
    dto.setWorkdayLogId(getWorkdayLogId().toString());
    return dto;
}


}
