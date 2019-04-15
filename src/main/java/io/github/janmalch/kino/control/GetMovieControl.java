package io.github.janmalch.kino.control;

import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.MovieRepository;
import javax.ws.rs.core.Response;

public class GetMovieControl implements Control<Movie> {
  private final long id;
  private final MovieRepository repository = new MovieRepository();

  public GetMovieControl(long id) {
    this.id = id;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Movie> result) {
    var movie = repository.find(id);
    if (movie == null) {
      return result.failure(Problem.builder(Response.Status.NOT_FOUND).instance().build());
    }
    return result.success(movie);
  }
}
