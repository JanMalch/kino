package io.github.janmalch.kino.control.movie;

import io.github.janmalch.kino.api.model.MovieInfoDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.CurrentMoviesSpec;
import io.github.janmalch.kino.util.Mapping;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.util.List;
import java.util.stream.Collectors;

public class GetCurrentMoviesControl implements Control<List<MovieInfoDto>> {

  private final Mapping<Movie, MovieInfoDto> mapper = new ReflectionMapper<>();
  private final Repository<Movie> repository = RepositoryFactory.createRepository(Movie.class);

  @Override
  public <T> T execute(ResultBuilder<T, List<MovieInfoDto>> result) {
    var list =
        repository
            .query(new CurrentMoviesSpec())
            .stream()
            .map(
                entity -> {
                  var dto = mapper.map(entity, MovieInfoDto.class);
                  var id =
                      entity.getPriceCategory() == null ? -1 : entity.getPriceCategory().getId();
                  dto.setPriceCategoryId(id);
                  return dto;
                })
            .collect(Collectors.toList());
    return result.success(list);
  }
}
