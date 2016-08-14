package com.veo.test


import org.springframework.ui.Model;

import com.veo.controller.UserController;
import com.veo.dao.UserDao;
import com.veo.service.UserService

import spock.lang.Specification

class UserControllerSpec extends Specification {

  private UserController controller = new UserController();

  private UserService userService;
  
  private Model model;
  
  def setup(){
    userService = Mock(UserService);
    model = Mock(Model);
    controller = new UserController(userService);
  }
  
  def "login goes to menu page" (){
    when: "login is called"
    String result = controller.login(null,model);
    then: "result is menu"
    result.equals("menu");
  }
  
  def "user service is called to retrieve an account" () {
    given: "a username"
    String username = "username";
    when: "login is called"
    String result = controller.login(username,model);
    then: "user service retrieves a user"
    1 * userService.findByUsername(username);
    then: "user is added to model"
    1 * model.addAttribute("user",_);
    
  }
  
  
  
  
  
}
