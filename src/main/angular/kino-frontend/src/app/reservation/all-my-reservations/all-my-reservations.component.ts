import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {ReservationInfoDto} from '@api/model/reservationInfoDto';
import {map, mergeMap, shareReplay, tap} from "rxjs/operators";
import {PresentationWithSeatsDto} from "@api/model/presentationWithSeatsDto";
import {PresentationService, ReservationService} from "@core/services";

export type ReservationInfoWithPresentation = ReservationInfoDto & { presentation: PresentationWithSeatsDto };

@Component({
  selector: 'app-all-my-reservations',
  templateUrl: './all-my-reservations.component.html',
  styleUrls: ['./all-my-reservations.component.scss']
})
export class AllMyReservationsComponent implements OnInit {

  reservations$: Observable<ReservationInfoWithPresentation[]>;
  loading = true;

  constructor(private reservationService: ReservationService,
              private presentationService: PresentationService) {
  }

  ngOnInit() {
    // temporary fix to resolve presentation data?
    this.reservations$ = this.presentationService.getAllPresentations().pipe(
      mergeMap(allPresentations => {
        const presentationMap = allPresentations.reduce((acc, curr) => {
          acc[curr.id] = curr;
          return acc;
        }, {});

        return this.reservationService.getMyReservations().pipe(
          map(reservations =>
            reservations.map(r => {
              return {
                ...r,
                presentation: presentationMap[r.presentationId]
              }
            })
          ),
          tap(() => this.loading = false),
          shareReplay(1)
        );
      })
    );
  }

}
