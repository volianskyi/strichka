package com.strichka.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "genre")
public class Genre {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "genre_type", nullable = false)
  private GenreType genreType;

  private static Genre valueOf(GenreType genreType) {
    return new Genre(genreType);
  }

  private Genre(GenreType genreType) {
    this.genreType = genreType;
  }

  @Setter(AccessLevel.PRIVATE)
  @ManyToMany(mappedBy = "genres")
  private Set<Movie> movies = new HashSet<>();

}
