package com.strichka.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "actor")
public class Actor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Pattern(regexp = "[A-Z][a-z]+",
          message = "Invalid name")
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Pattern(regexp = "[A-Z][a-z]+",
          message = "Invalid name")
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Setter(AccessLevel.PRIVATE)
  @ManyToMany(mappedBy = "actors")
  private Set<Movie> movies = new HashSet<>();
}
