package com.veo.exception;

public class GameNotFoundException extends Exception {
  private static final long serialVersionUID = -1698148292829990379L;

  public GameNotFoundException(String message) {
    super(message);
  }
  
}
