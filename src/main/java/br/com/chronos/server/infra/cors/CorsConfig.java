package br.com.chronos.server.infra.cors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.chronos.core.modules.global.interfaces.providers.EnvProvider;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Autowired
  private EnvProvider dotenvProvider;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    String allowedOrigin = dotenvProvider.get("WEB_APP_URL");
    if (allowedOrigin == null || allowedOrigin.isEmpty()) {
      throw new IllegalStateException("Missing required environment for variable for Web App Url ");
    }

    registry.addMapping("/**")
        .allowedOrigins(allowedOrigin)
        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
        .allowedHeaders("*")
        .allowCredentials(true);
  }

}
