package br.com.chronos.server.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.auth.use_cases.GetAccountUseCase;
import br.com.chronos.core.modules.global.interfaces.providers.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityJwtFilter extends OncePerRequestFilter {

  @Autowired
  JwtProvider jwtProvider;

  @Autowired
  AccountsRepository accountsRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
  throws ServletException, IOException {
    var token = recoverToken(request);
    if (token != null) {
      var subject =  jwtProvider.validateToken(token);
      var account = getAccount(subject);
      var securityUser = new SecurityUser(account);
      var authentication = new UsernamePasswordAuthenticationToken(securityUser,null,securityUser.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request,response);
  }

  private String recoverToken(HttpServletRequest request){
    var authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return null;
    }
    return authHeader.replace("Bearer ","");
  }
  private Account getAccount(String email){
    var useCase = new GetAccountUseCase(accountsRepository);
    var accountDto = useCase.execute(email);
    return new Account(accountDto);
  }
}
