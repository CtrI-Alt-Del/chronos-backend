package br.com.chronos.server.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.chronos.core.modules.global.interfaces.providers.EnvProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired
  private SecurityJwtFilter securityJwtFilter;

  @Autowired
  private EnvProvider dotenvProvider;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(crsf -> crsf.disable())
        .cors(cors -> cors.configurationSource(configurationSource()))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers("/collaborator").hasRole("MANAGER")
            .anyRequest().authenticated())
        .addFilterBefore(securityJwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  CorsConfigurationSource configurationSource() {
    var source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration defaultConfig = new CorsConfiguration();

    String allowedOrigin = dotenvProvider.get("WEB_APP_URL");
    if (allowedOrigin == null || allowedOrigin.isEmpty()) {
      throw new IllegalStateException("Missing required environment for variable for Web App Url ");
    }

    defaultConfig.setAllowedOrigins(List.of(allowedOrigin));
    defaultConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    defaultConfig.setAllowedHeaders(List.of("*"));
    source.registerCorsConfiguration("/**", defaultConfig);
    return source;
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
