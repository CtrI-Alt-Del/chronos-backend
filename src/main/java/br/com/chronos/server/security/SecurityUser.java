package br.com.chronos.server.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;

public class SecurityUser implements UserDetails {
  private final Collaborator collaborator;

  public SecurityUser(Collaborator collaborator) {
    this.collaborator = collaborator;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.collaborator.getRole().isAdmin()) {
      return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
          new SimpleGrantedAuthority("ROLE_USER"),
          new SimpleGrantedAuthority("ROLE_MANAGER"));

    } else if (this.collaborator.getRole().isManager()) {
      return List.of(new SimpleGrantedAuthority("ROLE_MANAGER"), new SimpleGrantedAuthority("ROLE_USER"));
    }
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return collaborator.getPassword().value();
  }

  @Override
  public String getUsername() {
    return collaborator.getEmail().value();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public Collaborator getCollaborator() {
    return collaborator;
  }
}
