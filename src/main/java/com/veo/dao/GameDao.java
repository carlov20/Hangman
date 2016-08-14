package com.veo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veo.model.Game;

public interface GameDao extends JpaRepository<Game, Long> {

}
