import {Component, Inject, Injectable, LOCALE_ID} from '@angular/core';
import {CrudService, GenericForm} from '@admin/services';
import {DefaultService} from '@api/api/default.service';
import {Observable} from 'rxjs';
import {SuccessMessage} from '@api/model/successMessage';
import {PresentationWithSeatsDto} from '@api/model/presentationWithSeatsDto';
import {NewPresentationDto} from '@api/model/newPresentationDto';
import {PresentationService} from '@core/services';
import {tap} from 'rxjs/operators';
import {ParseDatePipe} from '@shared/pipes';
import {DatePipe} from '@angular/common';

@Injectable()
export class PresentationCrudService implements CrudService<NewPresentationDto, PresentationWithSeatsDto> {

  private readonly parseDatePipe = new ParseDatePipe();
  private readonly datePipe;

  constructor(private api: DefaultService,
              private presentationService: PresentationService,
              @Inject(LOCALE_ID) locale: string) {
    this.datePipe = new DatePipe(locale);
  }


  create(dto: NewPresentationDto): Observable<number> {
    return this.api.newPresentation(dto).pipe(
      tap(() => this.presentationService.refresh())
    );
  }

  delete(id: number): Observable<SuccessMessage> {
    return this.api.deletePresentation(id).pipe(
      tap(() => this.presentationService.refresh())
    );
  }

  getForm(): GenericForm {
    return {
      date: {label: 'Datum & Uhrzeit', name: 'date', type: 'text', validation: {required: true}},
      movieId: {label: 'Film-ID', name: 'movieId', type: 'number', validation: {required: true}},
      cinemaHallId: {label: 'Saal-ID', name: 'cinemaHallId', type: 'number', validation: {required: true}}
    };
  }

  read(id: number): Observable<PresentationWithSeatsDto> {
    return this.presentationService.getPresentation(id);
  }

  readAll(): Observable<PresentationWithSeatsDto[]> {
    return this.presentationService.getAllPresentations();
  }

  update(id: number, dto: NewPresentationDto): Observable<SuccessMessage> {
    return this.api.updatePresentation(id, dto).pipe(
      tap(() => this.presentationService.refresh())
    );
  }

  isDisabled(checkFor: 'CREATE' | 'READ' | 'UPDATE' | 'DELETE' | 'READ_ALL'): boolean {
    return false;
  }

  transformReadForForm(read: PresentationWithSeatsDto): NewPresentationDto {
    return {
      date: this.formatAsYYYYMMDDHHmm(read.date) as unknown as Date,
      cinemaHallId: read.cinemaHallId,
      movieId: read.movie.id
    };
  }

  private formatAsYYYYMMDDHHmm(date: any): string {
    const parsed = this.parseDatePipe.transform(date);
    return this.datePipe.transform(parsed, 'yyyy-MM-dd\'T\'HH:mm:ss');
  }
}


@Component({
  selector: 'app-presentation',
  templateUrl: './presentation.component.html',
  styleUrls: ['./presentation.component.scss'],
  providers: [{provide: CrudService, useClass: PresentationCrudService}]
})
export class PresentationComponent {


  constructor(private crud: CrudService<NewPresentationDto, PresentationWithSeatsDto>) {
  }

  reservations(item: PresentationWithSeatsDto): string {
    const taken = item.seats.reduce((acc, curr) => acc + (curr.taken ? 1 : 0), 0);
    return `${taken} / ${item.seats.length}`;
  }

}
