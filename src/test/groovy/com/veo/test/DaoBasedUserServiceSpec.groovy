package com.veo.test

import com.veo.dao.UserDao
import com.veo.model.User;
import com.veo.service.DaoBasedUserService;

import spock.lang.Specification;

class DaoBasedUserServiceSpec extends Specification {
  
  private DaoBasedUserService service;
  
  private UserDao userDao;
  
  private User user;
  
  def setup(){
    userDao = Mock(UserDao);
    service = new DaoBasedUserService(userDao);
    user = new User(username:"username",id:1L);
  }
  
  def "existing user is returned " () {
    given: "mocked userDao"
    userDao.findByUsername(user.username) >> {Optional.of(user) };
    when: "find by username is called"
    User result = service.findByUsername(user.username);
    then: "result is user"
    result.equals(user);
  }
  
  def "non existing user is created and returned" () {
    given: "a non existing username"
    String username = "non existing user";
    and: "userDao returns empty optional"
    userDao.findByUsername(_) >> {Optional.empty()};
    when: "find by username is called"
    User result = service.findByUsername(username);
    then: "result has non existing username"
    result.username.equals(username);
    then: "save was called"
    1 * userDao.save(_);
    
  }

}
