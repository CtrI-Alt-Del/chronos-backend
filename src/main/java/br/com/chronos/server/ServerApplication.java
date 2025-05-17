package br.com.chronos.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import br.com.chronos.server.providers.env.DotenvProvider;

@SpringBootApplication
@EnableScheduling
public class ServerApplication {
  public static void main(String[] args) {
    var envProvider = new DotenvProvider();
    envProvider.loadEnv();
    SpringApplication.run(ServerApplication.class, args);
  }
}
