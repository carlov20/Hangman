package com.veo.service;

import com.veo.dto.LetterResult;
import com.veo.exception.GameNotCompleteException;
import com.veo.exception.GameNotFoundException;
import com.veo.model.Game;

public interface GameService {

  Game createGameFromUsername(String username);
  
  LetterResult clickedLetter(Long gameId, String letter);

  Game findGameFromUsername(String username) throws GameNotFoundException;

  Game completedGameById(Long gameId) throws GameNotFoundException, GameNotCompleteException;
}
