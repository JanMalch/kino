package io.github.janmalch.kino.control;

import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.MovieRepository;
import javax.ws.rs.core.Response;

public class RemoveMovieControl implements Control<Void> {

  private final long id;
  private final MovieRepository repository = new MovieRepository();

  public RemoveMovieControl(long id) {
    this.id = id;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Void> result) {
    var movie = repository.find(id);
    if (movie == null) {
      return result.failure(
          Problem.builder()
              .type("movie/not-found")
              .status(Response.Status.NOT_FOUND)
              .title("Unable to find movie with the given ID")
              .detail("No movie found for ID " + id)
              .instance()
              .build());
    }

    repository.remove(movie);
    return result.success(null, "Movie has been removed");
  }
}
