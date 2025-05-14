package br.com.chronos.server.api.services;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CollaborationService extends RestService {
  public CollaborationService() {
    super("collaboration");
  }

  public List<String> getManagersEmails(String collaboratorationSector) {
    return get("collaborators/managers/emails?sector=" + collaboratorationSector);
  }
}
