package com.veo.exception;

public class GameNotCompleteException extends Exception {

  private static final long serialVersionUID = 349042637482133378L;
  
  public GameNotCompleteException (String message) {
    super(message);
  }

}
