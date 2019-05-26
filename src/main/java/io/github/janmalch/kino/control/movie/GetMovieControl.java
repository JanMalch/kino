package io.github.janmalch.kino.control.movie;

import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import javax.ws.rs.core.Response;

public class GetMovieControl extends ManagingControl<Movie> {
  private final long id;
  private final Repository<Movie> repository = RepositoryFactory.createRepository(Movie.class);

  public GetMovieControl(long id) {
    this.id = id;
  }

  @Override
  public <T> T compute(ResultBuilder<T, Movie> result) {
    manage(repository);
    var movie = repository.find(id);
    if (movie == null) {
      return result.failure(Problem.builder(Response.Status.NOT_FOUND).instance().build());
    }
    return result.success(movie);
  }
}
