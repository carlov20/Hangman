package com.veo.test

import com.veo.dao.GameDao
import com.veo.dao.RandomWordDao
import com.veo.dto.LetterResult
import com.veo.exception.GameNotCompleteException;
import com.veo.exception.GameNotFoundException;
import com.veo.service.DaoBasedGameService
import com.veo.model.ChosenLetter
import com.veo.model.Game;
import com.veo.model.User
import com.veo.service.UserService

import spock.lang.Specification

class DaoBasedGameServiceSpec extends Specification {

  private DaoBasedGameService service;
  
  private UserService userService;
  private GameDao gameDao;
  private RandomWordDao randomWordDao;
  
  def setup(){
    userService = Mock(UserService);
    gameDao = Mock(GameDao);
    randomWordDao = Mock(RandomWordDao);
    service = new DaoBasedGameService(randomWordDao, gameDao, userService);
  }
  
  def "create game gets random word, creates game from it and returns" () {
    given: "a random word"
    String randomWord = "random";
    randomWordDao.getRandomWord() >> {randomWord};
    and: "a user"
    userService.findByUsername(_) >> {new User()};
    when: "create game from username is called"
    Game result = service.createGameFromUsername("username");
    then: "game has random word"
    result.word.equals(randomWord);
  }
  
  def "check letter exists in word on click letter" () {
    given: "A random word"
    String randomWord = "random";
    and: "A game returned with random word"
    Long id = 1L;
    gameDao.findOne(id) >> {new Game(word:randomWord,remainingGuesses:10)};
    when: "letter clicked is called"
    LetterResult result = service.clickedLetter(id,"a");
    then: "result has letter set"
    result.letter.equals("a");
    then: "indexes hit is populated"
    !result.indexesHit.isEmpty();
    result.indexesHit.get(0).equals(1);
    then: "remaining guesses stays the same"
    result.remainingGuesses.equals(10);
  }
  
  def "check letter doesn't exist in word on click letter" () {
    given: "A random word"
    String randomWord = "random";
    and: "A game returned with random word"
    Long id = 1L;
    gameDao.findOne(id) >> {new Game(word:randomWord,remainingGuesses:10)};
    when: "letter clicked is called"
    LetterResult result = service.clickedLetter(id,"q");
    then: "result has letter set"
    result.letter.equals("q");
    then: "indexes hit is empty"
    result.indexesHit.isEmpty();
    then: "remaining guesses is decreased"
    result.remainingGuesses.equals(9);
  }
  
  def "get game from username returns game" (){
    given: "a game"
    Game game = new Game(id:1);
    and: "a user with the game"
    User user = new User(username:"username",game:new Game(id:1));
    userService.findByUsername(user.getUsername()) >> {user};
    when: "find game from username is called"
    Game result = service.findGameFromUsername(user.getUsername());
    then: "correct game is returned"
    result.equals(game);
  }
  
  def "user without game throws exception" (){
    given: "a user"
    User user = new User(username:"username");
    userService.findByUsername(user.getUsername()) >> {user};
    when: "find game from username is called"
    service.findGameFromUsername(user.getUsername());
    then: "game not found exception is thrown"
    thrown GameNotFoundException
  }
  
  def "game completed with zero remaining guesses is returned from completed game by id" () {
    given: "a game with no remaining guesses"
    Game game = new Game(id:1,remainingGuesses:0);
    gameDao.findOne(game.getId()) >> {game};
    when: "completed game by id is called"
    Game result = service.completedGameById(game.getId());
    then: "game is returned"
    result.equals(game);
  }
  
  def "game completed with all letters is returned from completed game by id" () {
    given: "a game with all completed letters"
    Game game = new Game(id:1, word:"ab", remainingGuesses:5, chosenLetters:[new ChosenLetter("a"), new ChosenLetter("b")]);
    gameDao.findOne(game.getId()) >> {game};
    when: "completed game by id is called"
    Game result = service.completedGameById(game.getId());
    then: "game is returned"
    result.equals(game);
  }
  
  def "incomplete game is not returned from completed game by id" () {
    given: "an incomplete game"
    Game game = new Game(id:1, word:"ab", remainingGuesses:5, chosenLetters:[new ChosenLetter("a")]);
    gameDao.findOne(game.getId()) >> {game};
    when: "completed game by id is called"
    Game result = service.completedGameById(game.getId());
    then: "game not complete exception is thrown"
    thrown GameNotCompleteException
  }
  
  def "no id associated with game returned throws exception" () {
    when: "completed game by id is called"
    service.completedGameById(5L);
    then: "game not found exception is thrown"
    thrown GameNotFoundException
  }
  
}
