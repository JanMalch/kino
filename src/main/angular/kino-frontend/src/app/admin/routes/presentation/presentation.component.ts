import {Component, Injectable} from '@angular/core';
import {CrudService, GenericForm} from "@admin/services";
import {ReservationDto} from "@api/model/reservationDto";
import {ReservationInfoDto} from "@api/model/reservationInfoDto";
import {DefaultService} from "@api/api/default.service";
import {Observable} from "rxjs";
import {SuccessMessage} from "@api/model/successMessage";
import {PresentationWithSeatsDto} from "@api/model/presentationWithSeatsDto";
import {PresentationDto} from "@api/model/presentationDto";

@Injectable()
export class PresentationCrudService implements CrudService<PresentationDto, PresentationWithSeatsDto> {

  constructor(private api: DefaultService) {
  }

  create(dto: PresentationDto): Observable<number> {
    return undefined;
  }

  delete(id: number): Observable<SuccessMessage> {
    return undefined;
  }

  getForm(): GenericForm {
    return {
      date: {label: "Datum & Uhrzeit", name: "date", type: "text", validation: {required: true}},
      cinemaHallId: {label: "Saal-ID", name: "cinemaHallId", type: "number", validation: {required: true}}
    };
  }

  read(id: number): Observable<PresentationWithSeatsDto> {
    return this.api.getPresentation(id);
  }

  readAll(): Observable<PresentationWithSeatsDto[]> {
    return this.api.getAllReservations();
  }

  update(id: number, dto: PresentationDto): Observable<SuccessMessage> {
    return undefined;
  }

  isDisabled(checkFor: "CREATE" | "READ" | "UPDATE" | "DELETE" | "READ_ALL"): boolean {
    return false;
  }

}


@Component({
  selector: 'app-presentation',
  templateUrl: './presentation.component.html',
  styleUrls: ['./presentation.component.scss'],
  providers: [{provide: CrudService, useClass: PresentationCrudService}]
})
export class PresentationComponent {


  constructor(private crud: CrudService<ReservationDto, ReservationInfoDto>) {
  }

  resolveIcon(role: string): string {
    return role === "ADMIN" || role === "MODERATOR" ? "account-badge" : "account-circle";
  }

}
