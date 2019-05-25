import {Injectable, OnDestroy} from '@angular/core';
import {DefaultService} from '@api/api/default.service';
import {CinemaHallDto} from '@api/model/cinemaHallDto';
import {Observable} from 'rxjs';
import {untilDestroyed} from 'ngx-take-until-destroy';
import {map, shareReplay} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CinemaHallService implements OnDestroy {

  private readonly halls$: Observable<CinemaHallDto[]>;

  constructor(private api: DefaultService) {
    this.halls$ = this.api.getAllCinemaHalls().pipe(
      untilDestroyed(this),
      shareReplay(1)
    );
  }

  getCinemaHall(id: number): Observable<CinemaHallDto> {
    return this.halls$.pipe(
      map(halls => halls.find(hall => hall.id === id))
    );
  }

  ngOnDestroy() {
  }
}
