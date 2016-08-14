package com.veo.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestRandomWordDao implements RandomWordDao {

  private RestTemplate restTemplate;
  
  private String randomWordUrl;
  
  
  public RestRandomWordDao(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Value("${unpredictable.word.url}")
  public void setRandomWordUrl(String randomWordUrl) {
    this.randomWordUrl = randomWordUrl;
  }
  

  @Override
  public String getRandomWord() {
    ResponseEntity<String> randomWord = restTemplate.getForEntity(randomWordUrl, String.class);
    return randomWord.getBody();
  }

}
