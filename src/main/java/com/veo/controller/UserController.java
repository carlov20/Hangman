package com.veo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.veo.service.UserService;


@Controller
@RequestMapping("/login")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public String login(@RequestParam("username") String username, Model model) {
    model.addAttribute("user", userService.findByUsername(username));
    return "menu";
  }

}
