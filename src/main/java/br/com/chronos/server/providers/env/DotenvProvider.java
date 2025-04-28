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
    System.setProperty("S3_BUCKET_NAME", get("S3_BUCKET_NAME"));

    System.setProperty("MONGO_URI", get("MONGO_URI"));

    System.setProperty("POSTGRES_USER", get("POSTGRES_USER"));
    System.setProperty("POSTGRES_PASSWORD", get("POSTGRES_PASSWORD"));
    System.setProperty("POSTGRES_URL", get("POSTGRES_URL"));

    System.setProperty("RABBITMQ_HOST", get("RABBITMQ_HOST"));
    System.setProperty("RABBITMQ_USER", get("RABBITMQ_USER"));
    System.setProperty("RABBITMQ_PASSWORD", get("RABBITMQ_PASSWORD"));
  }
}
