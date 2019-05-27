import {Component, Injectable} from '@angular/core';
import {CrudService, GenericForm} from "@admin/services";
import {DefaultService} from "@api/api/default.service";
import {Observable} from "rxjs";
import {SuccessMessage} from "@api/model/successMessage";
import {PresentationWithSeatsDto} from "@api/model/presentationWithSeatsDto";
import {NewPresentationDto} from "@api/model/newPresentationDto";

@Injectable()
export class PresentationCrudService implements CrudService<NewPresentationDto, PresentationWithSeatsDto> {

  constructor(private api: DefaultService) {
  }

  create(dto: NewPresentationDto): Observable<number> {
    return this.api.newPresentation(dto);
  }

  delete(id: number): Observable<SuccessMessage> {
    return this.api.deletePresentation(id);
  }

  getForm(): GenericForm {
    return {
      date: {label: "Datum & Uhrzeit", name: "date", type: "text", validation: {required: true}},
      movieId: {label: "Film-ID", name: "movieId", type: "number", validation: {required: true}},
      cinemaHallId: {label: "Saal-ID", name: "cinemaHallId", type: "number", validation: {required: true}}
    };
  }

  read(id: number): Observable<PresentationWithSeatsDto> {
    return this.api.getPresentation(id);
  }

  readAll(): Observable<PresentationWithSeatsDto[]> {
    return this.api.getAllPresentations();
  }

  update(id: number, dto: NewPresentationDto): Observable<SuccessMessage> {
    return this.api.updatePresentation(id, dto);
  }

  isDisabled(checkFor: "CREATE" | "READ" | "UPDATE" | "DELETE" | "READ_ALL"): boolean {
    return false;
  }

  transformReadForForm(read: PresentationWithSeatsDto): NewPresentationDto {
    return {
      date: read.date,
      cinemaHallId: read.cinemaHallId,
      movieId: read.movie.id
    }
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
