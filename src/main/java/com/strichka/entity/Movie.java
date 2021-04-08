package com.strichka.entity;

import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "movie")
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "require")
  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "tag_line")
  private String tagLine;

  @Column(name = "story_line")
  private String storyLine;

  @NotBlank(message = "require")
  @Column(name = "country", nullable = false)
  private String country;

  @NotNull(message = "require")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "release_date", nullable = false)
  private LocalDate releaseDate;

  @ManyToMany
  @JoinTable(name = "movie_genre",
          joinColumns = @JoinColumn(name = "movie_id"),
          inverseJoinColumns = @JoinColumn(name = "genre_id")
  )
  private Set<Genre> genres = new HashSet<>();

  @ManyToMany
  @JoinTable(name = "movie_actor",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "actor_id")
  )
  private Set<Actor> actors = new HashSet<>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "director_id")
  private Director director;


  public void addActor(Actor actor) {
    actors.add(actor);
    actor.getMovies().add(this);
  }

  public void removeActor(Actor actor) {
    actors.remove(actor);
    actor.getMovies().remove(this);
  }

  public void addGenre(Genre genre) {
    genres.add(genre);
    genre.getMovies().add(this);
  }

  public void removeGenre(Genre genre) {
    genres.remove(genre);
    genre.getMovies().remove(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Movie movie = (Movie) o;
    return Objects.equals(id, movie.id);
  }

  @Override
  public int hashCode() {
    return 31;
  }
}
