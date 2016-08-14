package com.veo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.veo.dao.UserDao;
import com.veo.model.User;

@Service
public class DaoBasedUserService implements UserService {

  private UserDao userDao;

  public DaoBasedUserService(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public User findByUsername(String username) {
    Optional<User> result = userDao.findByUsername(username);
    return result.orElseGet(() -> {
      User newUser = new User();
      newUser.setUsername(username);
      userDao.save(newUser);
      return newUser;
    });
  }

}
