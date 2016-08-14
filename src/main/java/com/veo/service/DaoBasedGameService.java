package com.veo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.veo.dao.GameDao;
import com.veo.dao.RandomWordDao;
import com.veo.dto.GameStatus;
import com.veo.dto.LetterResult;
import com.veo.exception.GameNotCompleteException;
import com.veo.exception.GameNotFoundException;
import com.veo.model.ChosenLetter;
import com.veo.model.Game;
import com.veo.model.User;

@Service
public class DaoBasedGameService implements GameService {

  private RandomWordDao randomWordDao;
  private GameDao gameDao;
  private UserService userService;

  private Integer letterAttempts;

  private static final Logger LOGGER = LoggerFactory.getLogger(DaoBasedGameService.class);

  public DaoBasedGameService(RandomWordDao randomWordDao, GameDao gameDao,
      UserService userService) {
    this.randomWordDao = randomWordDao;
    this.gameDao = gameDao;
    this.userService = userService;
  }

  @Value("${letter.attempts:12}")
  public void setLetterAttempts(Integer letterAttempts) {
    this.letterAttempts = letterAttempts;
  }


  @Transactional
  @Override
  public Game createGameFromUsername(String username) {
    User user = userService.findByUsername(username);
    String randomWord = randomWordDao.getRandomWord().toLowerCase();
    // DEBUG PURPOSES ONLY. Should be removed from live
    LOGGER.info("random Word is: " + randomWord);
    Game game = new Game();
    game.setWord(randomWord);
    game.setRemainingGuesses(letterAttempts);
    user.setGame(game);
    game.setUser(user);
    gameDao.save(game);
    return game;
  }

  @Transactional
  @Override
  public LetterResult clickedLetter(Long gameId, String letter) {
    Game game = gameDao.findOne(gameId);
    game.chooseLetter(letter);
    LetterResult result = new LetterResult();
    result.setLetter(letter);
    result.setGameStatus(GameStatus.IN_PROGRESS);
    int index = game.getWord().indexOf(letter);
    List<Integer> indexes = new ArrayList<>();
    while (index >= 0) {
      indexes.add(index);
      index = game.getWord().indexOf(letter, index + 1);
    }
    if (indexes.isEmpty()) {
      game.setRemainingGuesses(game.getRemainingGuesses() - 1);
    }
    if(isGameFinished(game)){
      result.setGameStatus(GameStatus.COMPLETED);
    }
    
    result.setRemainingGuesses(game.getRemainingGuesses());
    result.setIndexesHit(indexes);
    return result;
  }
  
  private boolean isGameFinished(Game game){
    return game.getRemainingGuesses() <=0 || game.getWord().chars().mapToObj(wordLetter -> String.valueOf((char) wordLetter))
        .allMatch(wordLetter -> game.getChosenLetters().contains(new ChosenLetter(wordLetter)));
  }

  @Override
  public Game findGameFromUsername(String username) throws GameNotFoundException {
    User user = userService.findByUsername(username);
    if (user.getGame() == null) {
      throw new GameNotFoundException("No game associated with this user");
    }
    return user.getGame();
  }

  @Transactional
  @Override
  public Game completedGameById(Long gameId) throws GameNotFoundException, GameNotCompleteException {
    Game game = gameDao.findOne(gameId);
    if(game == null){
      throw new GameNotFoundException("No game associated with this id");
    }
    if(!isGameFinished(game)){
      throw new GameNotCompleteException("This game is not yet completed");
    }
    gameDao.delete(gameId);
    return game;
  }

}
