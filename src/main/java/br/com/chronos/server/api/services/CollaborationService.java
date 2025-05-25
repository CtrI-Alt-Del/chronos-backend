package br.com.chronos.server.api.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Service;

import br.com.chronos.core.collaboration.domain.dtos.CollaboratorDto;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.responses.PaginationResponse;

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
    return getCollaborator(response);
  }

  public PaginationResponse<CollaboratorDto> listCollaborators(String collaboratorName, int page) {
    var response = get("collaborators?page=" + page + "&name=" + collaboratorName);

    var item = (LinkedHashMap<String, Object>) response.get("items");
    var items = (List<LinkedHashMap<String, Object>>) item.get("items");
    var collaborators = Array.createFrom(items, this::getCollaborator);
    var itemsCount = (String) response.get("itemsCount");

    return new PaginationResponse<CollaboratorDto>(
        collaborators.list(),
        Integer.parseInt(itemsCount));
  }

  private CollaboratorDto getCollaborator(LinkedHashMap<String, Object> response) {
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
