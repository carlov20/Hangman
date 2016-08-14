package com.veo.dto;

import java.util.List;

public class LetterResult {

  private Integer remainingGuesses;
  private String letter;
  private List<Integer> indexesHit;
  private GameStatus gameStatus;
  
  
  public Integer getRemainingGuesses() {
    return remainingGuesses;
  }

  public void setRemainingGuesses(Integer remainingGuesses) {
    this.remainingGuesses = remainingGuesses;
  }

  public String getLetter() {
    return letter;
  }

  public void setLetter(String letter) {
    this.letter = letter;
  }

  public List<Integer> getIndexesHit() {
    
    return indexesHit;
  }

  public void setIndexesHit(List<Integer> indexesHit) {
    this.indexesHit = indexesHit;
  }

  public GameStatus getGameStatus() {
    return gameStatus;
  }

  public void setGameStatus(GameStatus gameStatus) {
    this.gameStatus = gameStatus;
  }
  
  


}
