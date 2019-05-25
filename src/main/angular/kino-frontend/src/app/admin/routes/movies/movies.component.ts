import {Component, Injectable} from '@angular/core';
import {CrudService, GenericForm} from "@admin/services";
import {DefaultService} from "@api/api/default.service";
import {Observable} from "rxjs";
import {SuccessMessage} from "@api/model/successMessage";
import {MovieDto} from "@api/model/movieDto";
import {map} from "rxjs/operators";


@Injectable()
export class MovieCrudService implements CrudService<MovieDto, MovieDto> {

  constructor(private api: DefaultService) {
  }

  create(dto: MovieDto): Observable<number> {
    return this.api.newMovie(dto);
  }

  delete(id: number): Observable<SuccessMessage> {
    return this.api.deleteMovie(id);
  }

  getForm(): GenericForm {
    return {
      name: {label: "Name", name: "name", type: "text", validation: {required: true, min: 1}},
      startDate: {label: "Startdatum", name: "startDate", type: "text", validation: {required: true}},
      endDate: {label: "Enddatum", name: "endDate", type: "text", validation: {required: true}},
      duration: {label: "Länge", name: "duration", type: "number", validation: {required: true}},
      ageRating: {label: "FSK", name: "ageRating", type: "number", validation: {required: true}}
    };
  }

  read(id: number): Observable<MovieDto> {
    return this.api.getMovie(id);
  }

  readAll(): Observable<MovieDto[]> {
    // TODO!
    return this.api.getCurrentMovies().pipe(
      map(current => {
        return Object.values(current.movies) as any;
      })
    );
  }

  update(id: number, dto: MovieDto): Observable<SuccessMessage> {
    return this.api.updateMovie(id, dto);
  }

}

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.scss'],
  providers: [{provide: CrudService, useClass: MovieCrudService}]
})
export class MoviesComponent {

  constructor(private crud: CrudService<MovieDto, MovieDto>) {
  }

  resolveIcon(role: string): string {
    return role === "ADMIN" || role === "MODERATOR" ? "account-badge" : "account-circle";
  }

}
