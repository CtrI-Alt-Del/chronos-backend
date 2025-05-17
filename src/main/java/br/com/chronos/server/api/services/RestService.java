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
  protected final String resource;

  @Value("${rest.url}")
  protected String URL;

  protected RestService(String resource) {
    this.resource = resource;
  }

  private String getUrl(String path) {
    return URL + "/" + resource + "/" + path;
  }

  private HttpHeaders getHeaders() {
    var headers = new HttpHeaders();
    headers.setBearerAuth(
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJjaHJvbm9zLXNlcnZlciIsInN1YiI6IntcImlkXCI6XCJkZWM1Zjk0Yi01ZDBhLTQ5OWMtOGIyMy0zMzFlMGEzZWFlZjlcIixcImVtYWlsXCI6XCJjaHJvbm9zLm1hbmFnZXJAZ21haWwuY29tXCIsXCJwYXNzd29yZFwiOlwiJDJhJDEwJGFUZlQ0REI2aE9EWGJreHhvOU1NMU8zSlBLdDJ6U3kvR05nNjJlQ29yNWV3ZGpzWFdFbDN5XCIsXCJpc0FjdGl2ZVwiOnRydWUsXCJyb2xlXCI6XCJtYW5hZ2VyXCIsXCJjb2xsYWJvcmF0aW9uU2VjdG9yXCI6XCJjb21lcmNpYWxcIixcImNvbGxhYm9yYXRvcklkXCI6XCI5YjFmZWJiMi02MGUyLTQzNjktODE0My1iNjk0MjliZjRmYjNcIn0iLCJleHAiOjE3NDc4MzM0NDB9.lpPWQQ2RvpKplUFZbE1ra3kF2U22BanHogEN0wUcioU");
    return headers;
  }

  protected LinkedHashMap<String, Object> get(String path) {
    var url = getUrl(path);
    var headers = getHeaders();
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
    var headers = new HttpHeaders();
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
