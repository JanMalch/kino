package io.github.janmalch.kino.control;

import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.MovieRepository;
import io.github.janmalch.kino.util.Contract;
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

    var affected = repository.remove(movie);
    Contract.affirm(affected == 1, "Delete action didn't remove one movie");
    return result.success(null);
  }
}
