package br.com.chronos.core.modules.solicitation.interfaces.repository;

import br.com.chronos.core.modules.solicitation.domain.entities.TimePunchLogAdjustmentSolicitation;

public interface SolicitationsRepository {
    void addTimePunchLogAdjustmentSolicitation(TimePunchLogAdjustmentSolicitation solicitation);
    
}
