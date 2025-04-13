package br.com.chronos.server.infra.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.chronos.core.auth.domain.entities.Account;

public class SecurityUser implements UserDetails {
  private final Account account;

  public SecurityUser(Account account) {
    this.account = account;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    if (account.getRole().isAdmin().value()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
      authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
    }
    if (account.getRole().isManager().value()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
    }
    authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    return authorities;
  }

  @Override
  public String getPassword() {
    return account.getPassword().value();
  }

  @Override
  public String getUsername() {
    return account.getEmail().value();
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
    return account.getIsActive().isTrue();
  }

  public Account getAccount() {
    return account;
  }
}
