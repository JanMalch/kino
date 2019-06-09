import {Pipe, PipeTransform} from '@angular/core';
import {MovieInfoDto} from '@api/model/movieInfoDto';

@Pipe({
  name: 'sortMovies'
})
export class SortMoviesPipe implements PipeTransform {

  transform(value: MovieInfoDto[]): MovieInfoDto[] {
    return [...value].sort((a, b) => a.name.toLowerCase().localeCompare(b.name.toLowerCase()));
  }

}
