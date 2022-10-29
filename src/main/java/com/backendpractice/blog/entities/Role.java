package com.backendpractice.blog.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role {

  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name")
  private String name;
}
