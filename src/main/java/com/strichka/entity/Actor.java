package com.strichka.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

  @NotBlank(message = "required")
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @NotBlank(message = "required")
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Setter(AccessLevel.PRIVATE)
  @ManyToMany(mappedBy = "actors")
  private Set<Movie> movies = new HashSet<>();
}
