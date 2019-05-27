import {Component, Injectable} from '@angular/core';
import {CrudService, GenericForm} from "@admin/services";
import {DefaultService} from "@api/api/default.service";
import {Observable} from "rxjs";
import {SuccessMessage} from "@api/model/successMessage";
import {ReservationInfoDto} from "@api/model/reservationInfoDto";
import {ReservationDto} from "@api/model/reservationDto";

@Injectable()
export class ReservationCrudService implements CrudService<ReservationDto, ReservationInfoDto> {

  constructor(private api: DefaultService) {
  }

  create(dto: ReservationDto): Observable<number> {
    return this.api.newReservation(dto);
  }

  delete(id: number): Observable<SuccessMessage> {
    return this.api.deleteReservationById(id);
  }

  getForm(): GenericForm {
    return {
      presentationId: {label: "ID der Vorstellung", name: "presentationId", type: "number", validation: {required: true, min: 1}},
      seatIds: {label: "Reservierte Sitze-IDs", name: "seatIds", type: "array", validation: {required: true}}
    };
  }

  read(id: number): Observable<ReservationInfoDto> {
    return this.api.getReservationById(id);
  }

  readAll(): Observable<ReservationInfoDto[]> {
    return this.api.getAllReservations();
  }

  update(id: number, dto: ReservationDto): Observable<SuccessMessage> {
    return this.api.updateReservationById(id, dto);
  }

  isDisabled(checkFor: "CREATE" | "READ" | "UPDATE" | "DELETE" | "READ_ALL"): boolean {
    return false;
  }

  transformReadForForm(read: ReservationInfoDto): ReservationDto {
    return read;
  }


}


@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.scss'],
  providers: [{provide: CrudService, useClass: ReservationCrudService}]
})
export class ReservationsComponent  {

  constructor(private crud: CrudService<ReservationDto, ReservationInfoDto>) {
  }

}
