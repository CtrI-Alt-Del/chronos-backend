package br.com.chronos.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.chronos.server.providers.env.DotenvProvider;

@SpringBootApplication
public class ServerApplication {

  public static void main(String[] args) {
    DotenvProvider dotenvProvider = new DotenvProvider();
    System.setProperty("DATABASE_SOURCE_URL", dotenvProvider.get("DATABASE_SOURCE_URL"));
    System.setProperty("DATABASE_USERNAME", dotenvProvider.get("DATABASE_USERNAME"));
    System.setProperty("DATABASE_PASSWORD", dotenvProvider.get("DATABASE_PASSWORD"));
    SpringApplication.run(ServerApplication.class, args);
  }

}
