package br.com.chronos.server.providers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.global.interfaces.providers.EnvProvider;
import br.com.chronos.core.global.interfaces.providers.JwtProvider;
import br.com.chronos.server.providers.authentication.SecurityAuthenticationProvider;
import br.com.chronos.server.providers.env.DotenvProvider;
import br.com.chronos.server.providers.jwt.Auth0JwtProvider;
// import br.com.chronos.server.providers.storage.S3StorageProvider;

@Configuration
public class ProvidersConfiguration {

  @Bean
  EnvProvider envProvider() {
    return new DotenvProvider();
  }

  @Bean
  JwtProvider jwtProvider() {
    return new Auth0JwtProvider(new DotenvProvider());
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
    return new SecurityAuthenticationProvider();
  }
  // @Bean
  // S3StorageProvider storageProvider(){
  //   return new S3StorageProvider(new DotenvProvider());
  // }
}
