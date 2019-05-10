package io.github.janmalch.kino.control.movie;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.MovieRepository;
import io.github.janmalch.kino.util.Mapping;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.ws.rs.core.Response;

public class UpdateMovieControl implements Control<SuccessMessage> {

  private final MovieDto movieDto;
  private final long movieId;
  private final MovieRepository repository = new MovieRepository();

  public UpdateMovieControl(MovieDto movieDto, long movieId) {
    this.movieDto = movieDto;
    this.movieId = movieId;
  }

  @Override
  public <T> T execute(ResultBuilder<T, SuccessMessage> result) {
    var refMovie = repository.find(movieId);
    if (refMovie == null) {
      return result.failure(Problem.builder(Response.Status.NOT_FOUND).instance().build());
    }

    var mapper = new UpdateMovieMapper();
    var entity = mapper.update(movieDto, refMovie);
    repository.update(entity);
    return result.success("Movie successfully updated");
  }

  static class UpdateMovieMapper implements Mapping<MovieDto, Movie> {

    private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

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
      if (update.getStartDate() != null) {
        try {
          existing.setStartDate(dayFormat.parse(update.getStartDate()));
        } catch (ParseException e) {
          // rethrow as unchecked as this should be handled by the validator
          throw new RuntimeException(e);
        }
      }
      if (update.getEndDate() != null) {
        try {
          existing.setEndDate(dayFormat.parse(update.getEndDate()));
        } catch (ParseException e) {
          // rethrow as unchecked as this should be handled by the validator
          throw new RuntimeException(e);
        }
      }

      return existing;
    }

    @Override
    public Movie map(MovieDto source) {
      throw new UnsupportedOperationException();
    }
  }
}
