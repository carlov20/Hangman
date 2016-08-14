package com.veo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChosenLetter implements Serializable {

  private static final long serialVersionUID = -8546007021791202513L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String letter;

  public ChosenLetter() {}

  public ChosenLetter(String letter) {
    this.letter = letter;
  }



  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLetter() {
    return letter;
  }

  public void setLetter(String letter) {
    this.letter = letter;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((letter == null) ? 0 : letter.hashCode());
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
    ChosenLetter other = (ChosenLetter) obj;
    if (letter == null) {
      if (other.letter != null)
        return false;
    } else if (!letter.equals(other.letter))
      return false;
    return true;
  }



}
