import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {CinemaHallService} from '@core/services';
import {MovieDto} from '@api/model/movieDto';

@Component({
  selector: 'app-movie-overview',
  templateUrl: './movie-overview.component.html',
  styleUrls: ['./movie-overview.component.scss']
})
export class MovieOverviewComponent implements OnInit {

  movie: MovieDto;


  myFilter = (d: Date): boolean => {
    const day = d.getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 6;
  };

  constructor(private route: ActivatedRoute,
              public cinemaHalls: CinemaHallService) {
  }

  ngOnInit() {
    this.movie = this.route.snapshot.data.movie;
  }

}
