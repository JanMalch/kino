import {Component, Injectable} from '@angular/core';
import {CrudService, GenericForm} from "@admin/services";
import {DefaultService} from "@api/api/default.service";
import {Observable} from "rxjs";
import {SuccessMessage} from "@api/model/successMessage";
import {CinemaHallDto} from "@api/model/cinemaHallDto";
import {NewCinemaHallDto} from "@api/model/newCinemaHallDto";

@Injectable()
export class CinemaHallCrudService implements CrudService<NewCinemaHallDto, CinemaHallDto> {

  constructor(private api: DefaultService) {
  }

  create(dto: NewCinemaHallDto): Observable<number> {
    return this.api.createCinemaHall(dto);
  }

  delete(id: number): Observable<SuccessMessage> {
    return this.api.deleteCinemaHall(id);
  }

  getForm(): GenericForm {
    return {
      name: {label: "Name", name: "name", type: "text", validation: {required: true, min: 1}},
      rowCount: {label: "Anzahl Reihen", name: "rowCount", type: "number", validation: {required: true}},
      seatsPerRow: {label: "Anzahl Sitze pro Reihe", name: "seatsPerRow", type: "number", validation: {required: true}}
    };
  }

  read(id: number): Observable<CinemaHallDto> {
    return this.api.getAccountById(id);
  }

  readAll(): Observable<CinemaHallDto[]> {
    return this.api.getAllCinemaHalls();
  }

  update(id: number, dto: NewCinemaHallDto): Observable<SuccessMessage> {
    return undefined;
  }

}

@Component({
  selector: 'app-cinemahall',
  templateUrl: './cinemahall.component.html',
  styleUrls: ['./cinemahall.component.scss'],
  providers: [{provide: CrudService, useClass: CinemaHallCrudService}]
})
export class CinemahallComponent  {

  constructor(private crud: CrudService<NewCinemaHallDto, CinemaHallDto>) {
  }

}
