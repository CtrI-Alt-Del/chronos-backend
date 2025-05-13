package br.com.chronos.server.providers.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

import br.com.chronos.core.notification.interfaces.CacheProvider;

@Component
public class RedisCacheProvider implements CacheProvider {
  @Autowired
  private StringRedisTemplate redisTemplate;

  @Override
  public void setWithMinutesToExpire(String key, String value, int minutesToExpire) {
    redisTemplate.opsForValue().set(key, value, minutesToExpire, TimeUnit.MINUTES);
  }

  @Override
  public void set(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public String get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public void delete(String key) {
    redisTemplate.delete(key);
  }
}
