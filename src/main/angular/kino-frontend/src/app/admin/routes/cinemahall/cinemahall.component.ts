import {Component, Injectable} from '@angular/core';
import {CrudService, GenericForm} from "@admin/services";
import {AccountInfoDto} from "@api/model/accountInfoDto";
import {DefaultService} from "@api/api/default.service";
import {Observable} from "rxjs";
import {SuccessMessage} from "@api/model/successMessage";
import {map} from "rxjs/operators";
import {CinemaHallDto} from "@api/model/cinemaHallDto";

@Injectable()
export class CinemaHallCrudService implements CrudService<AccountInfoDto, AccountInfoDto> {

  constructor(private api: DefaultService) {
  }

  create(dto: AccountInfoDto): Observable<number> {
    return undefined;
  }

  delete(id: number): Observable<SuccessMessage> {
    return undefined;
  }

  getForm(): GenericForm {
    return {
      name: {label: "Name", name: "name", type: "text", validation: {required: true, min: 1}},
      seatCount: {label: "Anzahl Sitze", name: "seatCount", type: "number", validation: {required: true}},
      rowCount: {label: "Anzahl Reihen", name: "rowCount", type: "number", validation: {required: true}}
    };
  }

  read(id: number): Observable<CinemaHallDto> {
    return this.api.getAccountById(id);
  }

  readAll(): Observable<CinemaHallDto[]> {
    return this.api.getAllCinemaHalls();
  }

  update(id: number, dto: AccountInfoDto): Observable<SuccessMessage> {
    return this.api.editAccountById(id, dto).pipe(
      map(() => ({
        message: "Account erfolgreich aktualisiert."
      }))
    );
  }

}

@Component({
  selector: 'app-cinemahall',
  templateUrl: './cinemahall.component.html',
  styleUrls: ['./cinemahall.component.scss'],
  providers: [{provide: CrudService, useClass: CinemaHallCrudService}]
})
export class CinemahallComponent  {

  constructor(private crud: CrudService<AccountInfoDto, AccountInfoDto>) {
  }

  resolveIcon(role: string): string {
    return role === "ADMIN" || role === "MODERATOR" ? "account-badge" : "account-circle";
  }

}
