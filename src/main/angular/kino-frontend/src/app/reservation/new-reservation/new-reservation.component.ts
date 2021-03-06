import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SeatDto} from '@api/model/seatDto';
import {AuthService} from '@core/auth';
import {Selectable} from '@shared/components';
import {MovieService, ReservationService} from '@core/services';
import {catchError, first, mergeMap, shareReplay} from 'rxjs/operators';
import {DefaultService} from '@api/api/default.service';
import {Observable, throwError} from 'rxjs';
import {MovieDto} from '@api/model/movieDto';

@Component({
  selector: 'app-new-reservation',
  templateUrl: './new-reservation.component.html',
  styleUrls: ['./new-reservation.component.scss']
})
export class NewReservationComponent implements OnInit {

  readonly presentationId: number;
  readonly columns = ['seat', 'price', 'discounted'];

  selectedSeats: SeatDto[] = [];
  priceForSeats: { [id: number]: number } = {};
  reducedPrice: number;
  regularPrice: number;

  loading = false;

  movie$: Observable<MovieDto>;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private api: DefaultService,
              private reservationService: ReservationService,
              public movies: MovieService,
              public auth: AuthService) {
    this.presentationId = parseInt(this.route.snapshot.queryParamMap.get('presentation'), 10) || null;
  }

  ngOnInit() {
    if (this.presentationId === null) {
      this.router.navigateByUrl('/404');
      return;
    }

    this.movie$ = this.api.getPresentation(this.presentationId).pipe(
      mergeMap(presentation => this.movies.getMovie(presentation.movie.id)),
      shareReplay(1)
    );

    this.movie$.pipe(first()).subscribe(m => {
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
    this.loading = true;
    this.reservationService.newReservation({
      presentationId: this.presentationId,
      seatIds: this.selectedSeats.map(s => s.id)
    }).pipe(
      catchError(err => {
        this.loading = false;
        return throwError(err);
      }),
      mergeMap(id => this.router.navigateByUrl(`/reservation/${id}`, {preserveQueryParams: false}))
    ).subscribe(() => this.loading = false);
  }
}
