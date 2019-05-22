import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SeatDto} from '@api/model/seatDto';
import {AuthService} from '@core/auth';
import {Selectable} from '@shared/components';
import {MovieService} from '@core/services';
import {first} from 'rxjs/operators';
import {DefaultService} from '@api/api/default.service';

@Component({
  selector: 'app-new-reservation',
  templateUrl: './new-reservation.component.html',
  styleUrls: ['./new-reservation.component.scss']
})
export class NewReservationComponent implements OnInit {

  readonly presentationId: number;
  readonly movieId: number;
  readonly columns = ['seat', 'price', 'discounted'];

  selectedSeats: SeatDto[] = [];
  priceForSeats: { [id: number]: number } = {};
  reducedPrice: number;
  regularPrice: number;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private api: DefaultService,
              public movies: MovieService,
              public auth: AuthService) {
    this.presentationId = parseInt(this.route.snapshot.queryParamMap.get('presentation'), 10) || null;
    this.movieId = parseInt(this.route.snapshot.queryParamMap.get('movie'), 10) || null;
  }

  ngOnInit() {
    this.movies.getMovie(this.movieId).pipe(first()).subscribe(m => {
      this.reducedPrice = m.priceCategory.reducedPrice;
      this.regularPrice = m.priceCategory.regularPrice;
    });
  }

  onSelect(seat: SeatDto & Selectable) {
    if (seat.selected) {
      this.selectedSeats = [...this.selectedSeats, seat].filter((dto, index, array) => {
        const i = array.findIndex(s => s.id === dto.id);
        return i === index;
      });
      this.priceForSeats[seat.id] = this.regularPrice;
    } else {
      delete this.priceForSeats[seat.id];
      this.selectedSeats = this.selectedSeats.filter(dto => dto.id !== seat.id);
    }
  }

  setAsDiscounted(seatId: number, discounted: boolean) {
    this.priceForSeats = {
      ...this.priceForSeats,
      [seatId]: discounted ? this.reducedPrice : this.regularPrice
    };
  }

  getTotalPrice(): number {
    return Object.values(this.priceForSeats).reduce((acc, curr) => acc + curr, 0);
  }

  makeReservation() {
    this.api.newReservation({
      presentationId: this.presentationId,
      seatIds: this.selectedSeats.map(s => s.id)
    }).subscribe(id => {
      this.router.navigateByUrl(`/reservation/${id}`, {preserveQueryParams: false});
    });
  }
}
