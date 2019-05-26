package io.github.janmalch.kino.control.movie;

import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.control.validation.BeanValidations;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javax.ws.rs.core.Response;

public class NewMovieControl extends ManagingControl<Long> {

  private final MovieDto movieDto;
  private final Repository<Movie> repository = RepositoryFactory.createRepository(Movie.class);

  public NewMovieControl(MovieDto movieDto) {
    this.movieDto = movieDto;
  }

  @Override
  public <T> T compute(ResultBuilder<T, Long> result) {
    manage(repository);
    var validationProblem = validate();
    if (validationProblem.isPresent()) {
      return result.failure(validationProblem.get());
    }

    var mapper = new NewMovieMapper();
    var entity = mapper.map(movieDto);
    repository.add(entity);
    return result.success(entity.getId());
  }

  Optional<Problem> validate() {
    var validator = new BeanValidations<>(movieDto, "new-movie");
    var dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    return validator
        .requireNotEmpty(
            "id",
            "name",
            "priceCategory",
            "startDate",
            "endDate",
            "duration",
            "ageRating") // exclude presentations from validation. can be empty
        .or(
            () -> {
              Date endDate;
              Date startDate;
              try {
                endDate = dayFormat.parse(movieDto.getEndDate());
                startDate = dayFormat.parse(movieDto.getStartDate());
              } catch (ParseException e) {
                // rethrow as unchecked as this should be handled by the validator
                throw new RuntimeException(e);
              }
              if (startDate.before(endDate)) {
                // valid
                return Optional.empty();
              } else {
                return Optional.of(
                    Problem.builder()
                        .type("new-movie/validation/dates")
                        .status(Response.Status.BAD_REQUEST)
                        .instance()
                        .title("Start date is after the end date")
                        .detail("The start date is after the end date")
                        .parameter("start", movieDto.getStartDate())
                        .parameter("end", movieDto.getEndDate())
                        .build());
              }
            });
  }

  static class NewMovieMapper implements Mapper<MovieDto, Movie> {

    private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Movie map(MovieDto source) {
      var movie = new Movie();
      movie.setName(source.getName());
      movie.setAgeRating(source.getAgeRating());
      movie.setDuration(source.getDuration());
      try {
        movie.setEndDate(dayFormat.parse(source.getEndDate()));
        movie.setStartDate(dayFormat.parse(source.getStartDate()));
      } catch (ParseException e) {
        // rethrow as unchecked as this should be handled by the validator
        throw new RuntimeException(e);
      }
      return movie;
    }
  }
}
