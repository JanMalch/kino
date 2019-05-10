package io.github.janmalch.kino.control.movie;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.MovieRepository;
import io.github.janmalch.kino.util.Mapper;
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
    var entity = mapper.updateEntity(movieDto, refMovie);
    repository.update(entity);
    return result.success("Movie successfully updated");
  }

  static class UpdateMovieMapper implements Mapper<Movie, MovieDto> {

    private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Movie updateEntity(MovieDto partialUpdate, Movie existingEntity) {
      if (partialUpdate.getName() != null) {
        existingEntity.setName(partialUpdate.getName());
      }
      if (partialUpdate.getAgeRating() != null) {
        existingEntity.setAgeRating(partialUpdate.getAgeRating());
      }
      if (partialUpdate.getDuration() != null) {
        existingEntity.setDuration(partialUpdate.getDuration());
      }
      if (partialUpdate.getStartDate() != null) {
        try {
          existingEntity.setStartDate(dayFormat.parse(partialUpdate.getStartDate()));
        } catch (ParseException e) {
          // rethrow as unchecked as this should be handled by the validator
          throw new RuntimeException(e);
        }
      }
      if (partialUpdate.getEndDate() != null) {
        try {
          existingEntity.setEndDate(dayFormat.parse(partialUpdate.getEndDate()));
        } catch (ParseException e) {
          // rethrow as unchecked as this should be handled by the validator
          throw new RuntimeException(e);
        }
      }

      return existingEntity;
    }
  }
}
