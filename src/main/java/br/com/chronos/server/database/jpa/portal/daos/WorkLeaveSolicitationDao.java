package br.com.chronos.server.database.jpa.portal.daos;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.portal.domain.records.SolicitationStatus;
import br.com.chronos.server.database.jpa.portal.models.WorkLeaveSolicitationModel;

public interface WorkLeaveSolicitationDao extends JpaRepository<WorkLeaveSolicitationModel, UUID> {
    Page<WorkLeaveSolicitationModel> findAllBySenderResponsibleAccountSectorAndIsVacationOrderByDateDesc(
            CollaborationSector.Sector sector,
            Boolean isVacation,
            PageRequest pageRequest);

    Page<WorkLeaveSolicitationModel> findAllBySolicitationStatusAndSenderResponsibleAccountSectorOrderByDateDesc(
            SolicitationStatus.Status status,
            CollaborationSector.Sector sector,
            PageRequest pageRequest);
}
