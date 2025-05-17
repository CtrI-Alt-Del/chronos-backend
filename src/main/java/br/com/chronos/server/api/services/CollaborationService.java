package br.com.chronos.server.api.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;

@Service
public class CollaborationService extends RestService {
  public CollaborationService() {
    super("collaboration");
  }

  public List<String> getManagersEmails(String collaboratorationSector) {
    var response = get("collaborators/managers/emails?sector=" + collaboratorationSector);
    if (response.get("item") instanceof List) {
      return (List<String>) response.get("item");
    }
    return new ArrayList<String>();
  }

  public CollaboratorDto getCollaborator(String collaboratorId) {
    var response = get("collaborators/" + collaboratorId);
    return new CollaboratorDto()
        .setId(response.get("id").toString())
        .setName(response.get("name").toString())
        .setEmail(response.get("email").toString())
        .setCpf(response.get("cpf").toString())
        .setRole(response.get("role").toString())
        .setSector(response.get("sector").toString())
        .setActive(Boolean.parseBoolean(response.get("isActive").toString()))
        .setWorkload(Byte.parseByte(response.get("workload").toString()));
  }
}
