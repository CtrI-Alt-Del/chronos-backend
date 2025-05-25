package br.com.chronos.server.api.services;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

public abstract class RestService {
  protected final RestTemplate rest = new RestTemplate();
  protected final HttpHeaders headers = new HttpHeaders();
  protected final String resource;
  protected final Map<String, String> queryParams = new HashMap<>();

  @Value("${rest.url}")
  protected String URL;

  protected RestService(String resource) {
    this.resource = resource;
  }

  public void setJwt(String authorizationHeader) {
    var jwt = authorizationHeader.replace("Bearer ", "");
    headers.setBearerAuth(jwt);
  }

  public void setServiceCode(String serviceCode) {
    headers.set("X-Service-Code", serviceCode);
  }

  public void setQueryParam(String key, String value) {
    queryParams.put(key, value);
  }

  private String getQueryParams() {
    return queryParams
        .entrySet()
        .stream()
        .map(entry -> entry.getKey() + "=" + entry.getValue())
        .collect(Collectors.joining("&"));
  }

  private String getUrl(String path) {
    return URL + "/" + resource + "/" + path + "?" + getQueryParams();
  }

  protected LinkedHashMap<String, Object> get(String path) {
    var url = getUrl(path);
    var requestEntity = new HttpEntity<>(headers);
    var response = rest.exchange(
        url,
        HttpMethod.GET,
        requestEntity,
        new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {
        });
    return response.getBody();
  }

  protected LinkedHashMap<String, Object> post(String path, Object body) {
    var url = getUrl(path);
    var requestEntity = new HttpEntity<>(body, headers);
    var response = rest.exchange(
        url,
        HttpMethod.POST,
        requestEntity,
        new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {
        });
    return response.getBody();
  }
}
