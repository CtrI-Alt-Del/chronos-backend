package br.com.chronos.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import br.com.chronos.server.providers.env.DotenvProvider;

@SpringBootApplication
@EnableScheduling
public class ServerApplication {
  public static void main(String[] args) {
    DotenvProvider dotenvProvider = new DotenvProvider();
    System.setProperty("MONGO_URL", dotenvProvider.get("MONGO_URL"));
    System.setProperty("DATABASE_URL", dotenvProvider.get("DATABASE_URL"));
    System.setProperty("POSTGRES_USER", dotenvProvider.get("POSTGRES_USER"));
    System.setProperty("POSTGRES_PASSWORD", dotenvProvider.get("POSTGRES_PASSWORD"));
    SpringApplication.run(ServerApplication.class, args);
  }
}
