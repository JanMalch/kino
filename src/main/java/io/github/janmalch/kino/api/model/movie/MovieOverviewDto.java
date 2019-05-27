package io.github.janmalch.kino.api.model.movie;

import java.util.*;

public class MovieOverviewDto {

  private List<DateGroup> weeks;
  private Map<Long, MovieInfoDto> movies;

  public List<DateGroup> getWeeks() {
    return weeks;
  }

  public void setWeeks(List<DateGroup> weeks) {
    this.weeks = weeks;
  }

  public Map<Long, MovieInfoDto> getMovies() {
    return movies;
  }

  public void setMovies(Map<Long, MovieInfoDto> movies) {
    this.movies = movies;
  }

  public static class DateGroup {

    private Date start;
    private Date end;
    private List<Long> movieIds = new LinkedList<>();

    public Date getStart() {
      return start;
    }

    public void setStart(Date start) {
      this.start = start;
    }

    public Date getEnd() {
      return end;
    }

    public void setEnd(Date end) {
      this.end = end;
    }

    public List<Long> getMovieIds() {
      return movieIds;
    }

    public void setMovieIds(List<Long> movieIds) {
      this.movieIds = movieIds;
    }

    public void init(Calendar start, long week) {
      var clone = (Calendar) start.clone();

      clone.add(Calendar.WEEK_OF_YEAR, Math.toIntExact(week));
      this.setStart(clone.getTime());

      clone.add(Calendar.DAY_OF_YEAR, 6);
      this.setEnd(clone.getTime());
    }
  }
}
