package br.com.chronos.server.providers.env;

import br.com.chronos.core.global.interfaces.providers.EnvProvider;
import io.github.cdimascio.dotenv.Dotenv;

public class DotenvProvider implements EnvProvider {
  private final Dotenv dotenv;

  public DotenvProvider() {
    dotenv = Dotenv.load();
  }

  @Override
  public String get(String key) {
    return dotenv.get(key);
  }

  @Override
  public void loadEnv() {
    System.setProperty("DATABASE_SOURCE_URL", get("DATABASE_SOURCE_URL"));
    System.setProperty("DATABASE_USERNAME", get("DATABASE_USERNAME"));
    System.setProperty("DATABASE_PASSWORD", get("DATABASE_PASSWORD"));

    System.setProperty("RABBITMQ_HOST", get("RABBITMQ_HOST"));
    System.setProperty("RABBITMQ_USER", get("RABBITMQ_USER"));
    System.setProperty("RABBITMQ_PASSWORD", get("RABBITMQ_PASSWORD"));
  }
}
