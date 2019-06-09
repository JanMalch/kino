package io.github.janmalch.kino.control.movie;

import io.github.janmalch.kino.api.model.PriceCategoryDto;
import io.github.janmalch.kino.api.model.movie.MovieDto;
import io.github.janmalch.kino.api.model.presentation.PresentationDto;
import io.github.janmalch.kino.control.presentation.PresentationDtoMapper;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.entity.PriceCategory;
import io.github.janmalch.kino.util.Mapper;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.util.stream.Collectors;

public class MovieDtoMapper implements Mapper<Movie, MovieDto> {
  private final Mapper<PriceCategory, PriceCategoryDto> priceCategoryDtoMapper =
      new ReflectionMapper<>(PriceCategoryDto.class);
  private final Mapper<Presentation, PresentationDto> presentationDtoMapper =
      new PresentationDtoMapper();

  @Override
  public MovieDto map(Movie source) {
    var dto = new MovieDto();
    dto.setId(source.getId());
    dto.setName(source.getName());
    dto.setDuration(source.getDuration());
    dto.setAgeRating(source.getAgeRating());
    dto.setImageURL(source.getImageURL());
    dto.setPresentations(
        source
            .getPresentations()
            .stream()
            .map(presentationDtoMapper::map)
            .collect(Collectors.toList()));
    if (source.getPriceCategory() != null) { // TODO: why check for null for MovieResourceTest?
      dto.setPriceCategory(priceCategoryDtoMapper.map(source.getPriceCategory()));
    }
    return dto;
  }
}
