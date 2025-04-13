package br.com.chronos.server.infra.security;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.collaboration.use_cases.EnsureSameCollaboratorionSectorUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCollaborationSectorFilter extends OncePerRequestFilter {
  private static final String UUID_REGEX = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

  private static final Pattern UUID_PATTERN = Pattern.compile(
      "^/collaboration/collaborators/([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})/(enable|disable)$");

  private static final List<String> FILTERED_PATTERNS = List.of(
      "^/collaboration/collaborators/" + UUID_REGEX + "/disable$",
      "^/collaboration/collaborators/" + UUID_REGEX + "/enable$");

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    System.out.println(FILTERED_PATTERNS.stream().noneMatch(path::matches));
    return FILTERED_PATTERNS.stream().noneMatch(path::matches);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain)
      throws ServletException, IOException {
    var path = request.getRequestURI();
    var matcher = UUID_PATTERN.matcher(path);

    if (matcher.matches()) {
      String collaboratorId = matcher.group(1); // Extracted UUID
      var accountCollaboratorId = (String) request.getAttribute("account collaborator id");
      ensureSameCollaboratorionSector(collaboratorId, accountCollaboratorId);
    }

    chain.doFilter(request, response);
  }

  private void ensureSameCollaboratorionSector(String collaboratorId, String accountCollaboratorId) {
    var useCase = new EnsureSameCollaboratorionSectorUseCase(collaboratorsRepository);
    useCase.execute(collaboratorId, accountCollaboratorId);
  }

}
