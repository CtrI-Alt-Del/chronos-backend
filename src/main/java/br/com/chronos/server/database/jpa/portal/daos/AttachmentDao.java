package br.com.chronos.server.database.jpa.portal.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chronos.server.database.jpa.portal.models.AttachmentModel;

public interface AttachmentDao extends JpaRepository<AttachmentModel, String> {
}
