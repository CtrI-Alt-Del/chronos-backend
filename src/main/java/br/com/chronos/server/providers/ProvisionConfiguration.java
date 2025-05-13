package br.com.chronos.server.providers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.global.interfaces.providers.EnvProvider;
import br.com.chronos.core.global.interfaces.providers.JwtProvider;
import br.com.chronos.core.global.interfaces.providers.StorageProvider;
import br.com.chronos.core.notification.interfaces.CacheProvider;
import br.com.chronos.core.notification.interfaces.EmailProvider;
import br.com.chronos.core.work_schedule.interfaces.PdfProvider;
import br.com.chronos.server.providers.authentication.SecurityAuthenticationProvider;
import br.com.chronos.server.providers.cache.RedisCacheProvider;
import br.com.chronos.server.providers.email.SpringEmailProvider;
import br.com.chronos.server.providers.env.DotenvProvider;
import br.com.chronos.server.providers.jwt.Auth0JwtProvider;
import br.com.chronos.server.providers.pdf.JasperPdfProvider;
import br.com.chronos.server.providers.storage.S3StorageProvider;

@Configuration
public class ProvisionConfiguration {
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

  @Bean
  EmailProvider emailProvider() {
    return new SpringEmailProvider();
  }

  @Bean
  CacheProvider cacheProvider() {
    return new RedisCacheProvider();
  }

  @Bean
  StorageProvider storageProvider() {
    return new S3StorageProvider(new DotenvProvider());
  }

  @Bean
  PdfProvider pdfProvider() {
    return new JasperPdfProvider();
  }
}
