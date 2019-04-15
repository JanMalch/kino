package io.github.janmalch.kino.control;

import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.MovieRepository;
import io.github.janmalch.kino.util.Mapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.ws.rs.core.Response;

public class UpdateMovieControl implements Control<Object> {

  private final MovieDto movieDto;
  private final long movieId;
  private final MovieRepository repository = new MovieRepository();

  public UpdateMovieControl(MovieDto movieDto, long movieId) {
    this.movieDto = movieDto;
    this.movieId = movieId;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Object> result) {
    var refMovie = repository.find(movieId);
    if (refMovie == null) {
      return result.failure(Problem.builder(Response.Status.NOT_FOUND).instance().build());
    }

    var mapper = new UpdateMovieMapper();
    var entity = mapper.mapToEntity(movieDto);
    entity.setId(movieId);
    repository.update(entity);
    return result.success(new Object());
  }

  static class UpdateMovieMapper implements Mapper<Movie, MovieDto> {

    private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Movie mapToEntity(MovieDto domain) {
      var movie = new Movie();
      if (domain.getName() != null) {
        movie.setName(domain.getName());
      }
      if (domain.getAgeRating() != null) {
        movie.setAgeRating(domain.getAgeRating());
      }
      if (domain.getDuration() != null) {
        movie.setDuration(domain.getDuration());
      }
      if (domain.getStartDate() != null) {
        try {
          movie.setStartDate(dayFormat.parse(domain.getStartDate()));
        } catch (ParseException e) {
          // rethrow as unchecked as this should be handled by the validator
          throw new RuntimeException(e);
        }
      }
      if (domain.getEndDate() != null) {
        try {
          movie.setEndDate(dayFormat.parse(domain.getEndDate()));
        } catch (ParseException e) {
          // rethrow as unchecked as this should be handled by the validator
          throw new RuntimeException(e);
        }
      }

      return movie;
    }
  }
}
