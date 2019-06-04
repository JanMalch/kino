import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ReservationService} from '@core/services';
import {SeatDto} from "@api/model/seatDto";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-view-reservation',
  templateUrl: './view-reservation.component.html',
  styleUrls: ['./view-reservation.component.scss']
})
export class ViewReservationComponent implements OnInit {

  readonly reservationId: number;

  readonly idMapper = (seat: SeatDto) => seat.id;

  constructor(private route: ActivatedRoute,
              public reservations: ReservationService,
              private snackBar: MatSnackBar,
              private router: Router) {
    this.reservationId = parseInt(this.route.snapshot.paramMap.get('id'), 10) || null;
  }

  ngOnInit() {
  }

  deleteReservation() {
    this.reservations.deleteMyReservation(this.reservationId)
      .subscribe(msg => {
        this.snackBar.open(msg.message, null, { duration: 2500 });
        this.router.navigateByUrl("/")
      })
  }

}
