package com.veo.test

import org.springframework.ui.Model;

import com.veo.controller.GameController
import com.veo.service.GameService;

import spock.lang.Specification

class GameControllerSpec extends Specification {

  private GameController controller;
  
  private GameService gameService;
  
  private Model model;
  
  def setup() {
    gameService = Mock(GameService);
    controller = new GameController(gameService);
    model = Mock(Model);
  }
  
  def "new game creates game via service" () {
    given: "a username"
    String username = "username";
    when: "new game is called"
    String result = controller.newGame(username,model);
    then: "result is game page"
    result.equals("game");
    then: "create game is called"
    1 * gameService.createGameFromUsername(username);
    then: "game is added to model"
    1 * model.addAttribute("game",_);
    
  }
  
  def "continue game gets game via service" () {
    given: "a username"
    String username = "username";
    when: "continue game is called"
    String result = controller.continueGame(username,model);
    then: "result is game page"
    result.equals("game");
    then: "find game is called"
    1 * gameService.findGameFromUsername(username);
    then: "game is added to model"
    1 * model.addAttribute("game",_);
  }
  
  def "letter clicked calls game service" () {
    when: "letter clicked is called"
    controller.letterClicked(1L,"a");
    then: "service is called"
    1 * gameService.clickedLetter(1L,"a");
  }
  
  def "completed game gets game via service" () {
    when: "completed game is called"
    String result = controller.completedGame(1L,model);
    then: "result is completed"
    result.equals("completed")
    then: "game service is called"
    1 * gameService.completedGameById(1L);
    then: "game is added to model"
    1 * model.addAttribute("game",_);
  }
  
}
