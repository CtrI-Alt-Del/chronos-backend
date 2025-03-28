package br.com.chronos.core.modules.solicitation.interfaces.repository;

import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;
import br.com.chronos.core.modules.solicitation.domain.entities.WorkScheduleAdjustmentSolicitation;

public interface SolicitationsRepository {
    void addTimePunchLogAdjustmentSolicitation(TimePunchLogAdjustmentSolicitation solicitation);
    void addWorkScheduleAdjustmentSolicitation(WorkScheduleAdjustmentSolicitation solicitation);
    
}
