package com.veo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veo.model.User;

public interface UserDao extends JpaRepository<User, Long> {
  
  Optional<User> findByUsername(String username);

}
