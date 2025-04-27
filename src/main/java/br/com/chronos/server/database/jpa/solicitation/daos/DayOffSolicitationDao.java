package br.com.chronos.server.database.jpa.solicitation.daos;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.server.database.jpa.solicitation.models.DayOffSolicitationModel;

public interface DayOffSolicitationDao extends JpaRepository<DayOffSolicitationModel, UUID> {
  Page<DayOffSolicitationModel> findAllBySenderResponsibleAccountSectorOrderByDateDesc(
      CollaborationSector.Sector sector,
      PageRequest pageRequest);
}