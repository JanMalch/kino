import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ReservationService} from '@core/services';
import {SeatForReservationDto} from '@api/model/seatForReservationDto';

@Component({
  selector: 'app-view-reservation',
  templateUrl: './view-reservation.component.html',
  styleUrls: ['./view-reservation.component.scss']
})
export class ViewReservationComponent implements OnInit {

  readonly reservationId: number;

  readonly idMapper = (seat: SeatForReservationDto) => seat.id;

  constructor(private route: ActivatedRoute,
              public reservations: ReservationService) {
    this.reservationId = parseInt(this.route.snapshot.paramMap.get('id'), 10) || null;
  }

  ngOnInit() {
  }

}
