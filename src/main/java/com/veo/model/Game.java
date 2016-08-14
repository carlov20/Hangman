package com.veo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Game implements Serializable {

  private static final long serialVersionUID = -1732823458437823429L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Integer remainingGuesses;

  private String word;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "gameId")
  private Set<ChosenLetter> chosenLetters;

  @OneToOne()
  @JoinColumn(name = "userId")
  private User user;

  public Game() {
    chosenLetters = new HashSet<>();

  }

  public Long getId() {

    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getRemainingGuesses() {
    return remainingGuesses;
  }

  public void setRemainingGuesses(Integer remainingGuesses) {
    this.remainingGuesses = remainingGuesses;
  }

  public Set<ChosenLetter> getChosenLetters() {
    return chosenLetters;
  }

  public void setChosenLetters(Set<ChosenLetter> chosenLetters) {
    this.chosenLetters = chosenLetters;
  }

  public String getWord() {

    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void chooseLetter(String letter){
    ChosenLetter chosen = new ChosenLetter();
    chosen.setLetter(letter);
    getChosenLetters().add(chosen);
  }
  
  public boolean letterChosen(String letter) {
    ChosenLetter chosen = new ChosenLetter();
    chosen.setLetter(letter.toLowerCase());
    return chosenLetters.contains(chosen);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Game other = (Game) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }



}
