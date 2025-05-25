package br.com.chronos.server.api.services;

import java.util.LinkedHashMap;

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

  private String getUrl(String path) {
    return URL + "/" + resource + "/" + path;
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
