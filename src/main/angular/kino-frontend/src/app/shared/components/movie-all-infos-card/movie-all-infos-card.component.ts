import {Component, Input, OnInit} from '@angular/core';
import {MovieDto} from '@api/model/movieDto';

@Component({
  selector: 'app-movie-all-infos-card',
  templateUrl: './movie-all-infos-card.component.html',
  styleUrls: ['./movie-all-infos-card.component.scss']
})
export class MovieAllInfosCardComponent implements OnInit {

  @Input() movie: MovieDto;

  readonly fallbackImage = 'https://source.unsplash.com/random/300x400';

  constructor() { }

  ngOnInit() {
  }

}
