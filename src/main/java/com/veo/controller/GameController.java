package com.veo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.veo.dto.LetterResult;
import com.veo.exception.GameNotCompleteException;
import com.veo.exception.GameNotFoundException;
import com.veo.service.GameService;

@Controller
@RequestMapping("/game")
public class GameController {

  private GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }



  @RequestMapping(value = "/create/{username}", method = RequestMethod.GET)
  public String newGame(@PathVariable("username") String username, Model model) {
    model.addAttribute("game", gameService.createGameFromUsername(username));
    return "game";
  }
  
  @RequestMapping(value = "/continue/{username}", method = RequestMethod.GET)
  public String continueGame(@PathVariable("username") String username, Model model) throws GameNotFoundException {
    model.addAttribute("game", gameService.findGameFromUsername(username));
    return "game";
  }

  @RequestMapping(value = "/click/{gameId}/{letter}", method = RequestMethod.GET)
  public @ResponseBody LetterResult letterClicked(@PathVariable("gameId") Long gameId,
      @PathVariable("letter") String letter) {
    return gameService.clickedLetter(gameId, letter.toLowerCase());
  }
  
  @RequestMapping(value = "/complete/{gameId}", method = RequestMethod.GET)
  public String completedGame(@PathVariable("gameId") Long gameId, Model model) throws GameNotFoundException, GameNotCompleteException {
    model.addAttribute("game", gameService.completedGameById(gameId));
    return "completed";
  }

}
