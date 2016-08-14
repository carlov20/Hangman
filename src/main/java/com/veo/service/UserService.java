package com.veo.service;
import com.veo.model.User;

public interface UserService {

  User findByUsername(String username);
}
