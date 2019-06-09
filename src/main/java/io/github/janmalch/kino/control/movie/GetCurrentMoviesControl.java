package io.github.janmalch.kino.control.movie;

import io.github.janmalch.kino.api.model.movie.MovieInfoDto;
import io.github.janmalch.kino.api.model.movie.MovieOverviewDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.CurrentMoviesSpec;
import io.github.janmalch.kino.util.Mapper;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class GetCurrentMoviesControl extends ManagingControl<MovieOverviewDto> {

  private final Mapper<Movie, MovieInfoDto> mapper = new ReflectionMapper<>(MovieInfoDto.class);
  private final Repository<Movie> repository = RepositoryFactory.createRepository(Movie.class);

  @Override
  public <T> T compute(ResultBuilder<T, MovieOverviewDto> result) {
    var overview = new MovieOverviewDto();
    manage(repository);

    var queryResult = repository.query(new CurrentMoviesSpec(repository));

    var movieInfoDtoMap =
        queryResult
            .stream()
            .collect(
                Collectors.toMap(
                    Movie::getId,
                    entity -> {
                      var dto = mapper.map(entity);
                      var id =
                          entity.getPriceCategory() == null
                              ? -1
                              : entity.getPriceCategory().getId();
                      dto.setPriceCategoryId(id);

                      return dto;
                    }));
    overview.setMovies(movieInfoDtoMap);
    System.out.println(movieInfoDtoMap);

    var weeksMap = new HashMap<Long, MovieOverviewDto.DateGroup>();

    var now = new Date();
    var startOfWeekForNow = getStartOfWeek(now);

    var movieWeeksMap =
        queryResult
            .stream()
            .collect(
                Collectors.toMap(
                    Movie::getId,
                    entity ->
                        entity
                            .getPresentations()
                            .stream()
                            .map(Presentation::getDate)
                            .map(date -> weeksBetween(now, date))
                            .filter(weeks -> weeks >= 0)
                            .collect(Collectors.toSet())));

    movieWeeksMap.forEach(
        (movieId, weeks) -> {
          weeks.forEach(
              weekIndex -> {
                var group = weeksMap.getOrDefault(weekIndex, new MovieOverviewDto.DateGroup());
                group.getMovieIds().add(movieId);
                if (!weeksMap.containsKey(weekIndex)) {
                  group.init(startOfWeekForNow, weekIndex);
                  weeksMap.put(weekIndex, group);
                }
              });
        });

    var weeks = new ArrayList<>(weeksMap.values());
    overview.setWeeks(weeks);
    return result.success(overview);
  }

  Calendar getStartOfWeek(Date date) {
    // get today and clear time of day
    var cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
    cal.clear(Calendar.MINUTE);
    cal.clear(Calendar.SECOND);
    cal.clear(Calendar.MILLISECOND);

    // get start of this week in milliseconds
    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
    return cal;
  }

  long weeksBetween(Date d1, Date d2) {
    var c1 = new GregorianCalendar();
    c1.setTime(d1);
    var c2 = new GregorianCalendar();
    c2.setTime(d2);
    return weeksBetween(c1, c2);
  }

  // https://stackoverflow.com/a/25935422
  long weeksBetween(Calendar d1, Calendar d2) {
    Instant d1i = Instant.ofEpochMilli(d1.getTimeInMillis());
    Instant d2i = Instant.ofEpochMilli(d2.getTimeInMillis());

    LocalDateTime startDate = LocalDateTime.ofInstant(d1i, ZoneId.systemDefault());
    LocalDateTime endDate = LocalDateTime.ofInstant(d2i, ZoneId.systemDefault());

    return ChronoUnit.WEEKS.between(startDate, endDate);
  }
}
