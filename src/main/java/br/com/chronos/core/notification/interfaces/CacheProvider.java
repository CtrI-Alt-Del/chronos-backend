package br.com.chronos.core.notification.interfaces;

public interface CacheProvider {
  void set(String key, String value);

  void setWithMinutesToExpire(String key, String value, int minutesToExpire);

  String get(String key);

  void delete(String key);
}
