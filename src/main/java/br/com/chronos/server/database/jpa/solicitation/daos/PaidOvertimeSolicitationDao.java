package br.com.chronos.server.database.jpa.solicitation.daos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.server.database.jpa.solicitation.models.PaidOvertimeSolicitationModel;

public interface PaidOvertimeSolicitationDao extends JpaRepository<PaidOvertimeSolicitationModel, UUID> {

}