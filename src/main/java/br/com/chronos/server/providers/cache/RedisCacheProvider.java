package br.com.chronos.server.providers.cache;

import br.com.chronos.core.notification.interfaces.CacheProvider;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisCacheProvider implements CacheProvider {
  private final StringRedisTemplate redisTemplate;

  public RedisCacheProvider(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void setWithMinutesToExpire(String key, String value, int minutesToExpire) {
    throw new UnsupportedOperationException("Unimplemented method 'setWithMinutesToExpire'");
  }

  @Override
  public void set(String key, String value) {
    throw new UnsupportedOperationException("Unimplemented method 'set'");
  }

  @Override
  public String get(String key) {
    throw new UnsupportedOperationException("Unimplemented method 'get'");
  }

  @Override
  public void delete(String key) {
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }
}
