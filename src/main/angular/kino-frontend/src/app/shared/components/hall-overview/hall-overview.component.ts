import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CinemaHallService} from '@core/services';
import {DefaultService} from '@api/api/default.service';
import {Observable} from 'rxjs';
import {PresentationWithSeatsDto} from '@api/model/presentationWithSeatsDto';
import {map, tap} from 'rxjs/operators';
import {SeatForPresentationDto} from '@api/model/seatForPresentationDto';
import {SeatDto} from '@api/model/seatDto';
import {SeatForReservationDto} from '@api/model/seatForReservationDto';

@Component({
  selector: 'app-hall-overview[presentationId]',
  templateUrl: './hall-overview.component.html',
  styleUrls: ['./hall-overview.component.scss']
})
export class HallOverviewComponent implements OnInit {

  @Input() presentationId: number;
  @Input() selectable = true;
  @Input() selectedSeats: number[] = [];
  @Output() select = new EventEmitter<SeatForPresentationDto & Selectable>();

  presentation: PresentationWithSelectableSeatsDto;

  constructor(private api: DefaultService) {
  }

  ngOnInit() {
    this.api.getPresentation(this.presentationId).pipe(
      map(p => {
        return {
          ...p,
          seats: [...p.seats.map(s => ({...s, selected: false}))]
        };
      })
    ).subscribe(res => {
      res.seats.forEach(seat => {
          seat.selected = this.selectedSeats.includes(seat.id);
      });
      this.presentation = res;
    });
  }

  onSelect(seat: SeatForPresentationDto & Selectable) {
    if (this.selectable && !seat.taken) {
      seat.selected = !seat.selected;
      this.presentation = {
        ...this.presentation,
        seats: [...this.presentation.seats]
      };
      this.select.emit(seat);
    }

  }

}

export interface Selectable {
  selected: boolean;
}

export interface PresentationWithSelectableSeatsDto extends PresentationWithSeatsDto {
  seats: Array<SeatForPresentationDto & Selectable>;
}
