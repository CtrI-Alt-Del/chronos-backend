package br.com.chronos.server.security;

import java.util.ArrayList;
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
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    if (collaborator.getRole().isAdmin().value()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
      authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
    }
    if(collaborator.getRole().isManager().value()){
      authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
    }
    authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    return authorities;
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
    return collaborator.getIsActive().value();
  }

  public Collaborator getCollaborator() {
    return collaborator;
  }
}
