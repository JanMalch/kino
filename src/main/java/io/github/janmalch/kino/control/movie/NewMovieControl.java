package io.github.janmalch.kino.control.movie;

import io.github.janmalch.kino.api.model.movie.NewMovieDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.control.validation.BeanValidations;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapper;
import java.util.Optional;
import javax.validation.Valid;

public class NewMovieControl extends ManagingControl<Long> {

  private final NewMovieDto movieDto;
  private final Repository<Movie> repository = RepositoryFactory.createRepository(Movie.class);

  public NewMovieControl(@Valid NewMovieDto movieDto) {
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
    return validator.requireNotEmpty(
        "id",
        "name",
        "priceCategory",
        "duration",
        "imageURL",
        "ageRating") // exclude presentations from validation. can be empty
    ;
  }

  static class NewMovieMapper implements Mapper<NewMovieDto, Movie> {

    @Override
    public Movie map(@Valid NewMovieDto source) {
      var movie = new Movie();
      movie.setName(source.getName());
      movie.setAgeRating(source.getAgeRating());
      movie.setDuration(source.getDuration());
      return movie;
    }
  }
}
