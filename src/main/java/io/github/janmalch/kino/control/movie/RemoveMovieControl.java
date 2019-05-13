package io.github.janmalch.kino.control.movie;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import javax.ws.rs.core.Response;

public class RemoveMovieControl implements Control<SuccessMessage> {

  private final long id;
  private final Repository<Movie> repository = RepositoryFactory.createRepository(Movie.class);

  public RemoveMovieControl(long id) {
    this.id = id;
  }

  @Override
  public <T> T execute(ResultBuilder<T, SuccessMessage> result) {
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
    return result.success("Movie has been removed");
  }
}
