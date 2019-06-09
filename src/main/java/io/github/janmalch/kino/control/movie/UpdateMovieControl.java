package io.github.janmalch.kino.control.movie;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.movie.MovieDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapper;
import javax.ws.rs.core.Response;

public class UpdateMovieControl extends ManagingControl<SuccessMessage> {

  private final MovieDto movieDto;
  private final long movieId;
  private final Repository<Movie> repository = RepositoryFactory.createRepository(Movie.class);

  public UpdateMovieControl(MovieDto movieDto, long movieId) {
    this.movieDto = movieDto;
    this.movieId = movieId;
  }

  @Override
  public <T> T compute(ResultBuilder<T, SuccessMessage> result) {
    manage(repository);
    var refMovie = repository.find(movieId);
    if (refMovie == null) {
      return result.failure(Problem.builder(Response.Status.NOT_FOUND).instance().build());
    }

    var mapper = new UpdateMovieMapper();
    var entity = mapper.update(movieDto, refMovie);
    repository.update(entity);
    return result.success("Movie successfully updated");
  }

  static class UpdateMovieMapper implements Mapper<MovieDto, Movie> {

    @Override
    public Movie update(MovieDto update, Movie existing) {
      if (update.getName() != null) {
        existing.setName(update.getName());
      }
      if (update.getAgeRating() != null) {
        existing.setAgeRating(update.getAgeRating());
      }
      if (update.getDuration() != null) {
        existing.setDuration(update.getDuration());
      }
      if (update.getImageURL() != null) {
        existing.setImageURL(update.getImageURL());
      }
      return existing;
    }
  }
}
