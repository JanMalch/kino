import {Component, Inject, Injectable, LOCALE_ID} from '@angular/core';
import {CrudService, GenericForm} from "@admin/services";
import {DefaultService} from "@api/api/default.service";
import {Observable} from "rxjs";
import {SuccessMessage} from "@api/model/successMessage";
import {MovieDto} from "@api/model/movieDto";
import {NewMovieDto} from "@api/model/newMovieDto";
import {MovieService} from "@core/services";
import {tap} from "rxjs/operators";
import {ParseDatePipe} from "@shared/pipes";
import {DatePipe} from "@angular/common";


@Injectable()
export class MovieCrudService implements CrudService<NewMovieDto, MovieDto> {

  private readonly parseDatePipe = new ParseDatePipe();
  private readonly datePipe;

  constructor(private api: DefaultService,
              private movieService: MovieService,
              @Inject(LOCALE_ID) locale: string) {
    this.datePipe = new DatePipe(locale);
  }


  create(dto: NewMovieDto): Observable<number> {
    return this.api.newMovie(dto).pipe(
      tap(() => this.movieService.refresh())
    );
  }

  delete(id: number): Observable<SuccessMessage> {
    return this.api.deleteMovie(id).pipe(
      tap(() => this.movieService.refresh())
    );
  }

  getForm(): GenericForm {
    return {
      name: {label: "Name", name: "name", type: "text", validation: {required: true, min: 1}},
      startDate: {label: "Startdatum", name: "startDate", type: "text", validation: {required: true}},
      endDate: {label: "Enddatum", name: "endDate", type: "text", validation: {required: true}},
      duration: {label: "Länge", name: "duration", type: "number", validation: {required: true}},
      ageRating: {label: "FSK", name: "ageRating", type: "number", validation: {required: true}},
      imageURL: {label: "Poster-URL", name: "imageURL", type: "text"},
      priceCategoryId: {label: "Preiskategorie", name: "priceCategoryId", type: "number", validation: {required: true}}
    };
  }

  read(id: number): Observable<MovieDto> {
    return this.api.getMovie(id);
  }

  readAll(): Observable<MovieDto[]> {
    return this.api.getAllMovies();
  }

  update(id: number, dto: NewMovieDto): Observable<SuccessMessage> {
    return this.api.updateMovie(id, dto).pipe(
      tap(() => this.movieService.refresh())
    );
  }

  transformReadForForm(read: MovieDto): NewMovieDto {
    return {
      ...read,
      startDate: this.formatAsYYYYMMDD(read.startDate),
      endDate: this.formatAsYYYYMMDD(read.endDate),
      priceCategoryId: read.priceCategory.id
    };
  }

  isDisabled(checkFor: "CREATE" | "READ" | "UPDATE" | "DELETE" | "READ_ALL"): boolean {
    return false;
  }

  private formatAsYYYYMMDD(date: any): string {
    const parsed = this.parseDatePipe.transform(date);
    return this.datePipe.transform(parsed, 'yyyy-MM-dd');
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
