import {Component, Input, OnInit} from '@angular/core';
import {MovieInfoDto} from '@api/model/movieInfoDto';

@Component({
  selector: 'app-movie-card[movie]',
  templateUrl: './movie-card.component.html',
  styleUrls: ['./movie-card.component.scss']
})
export class MovieCardComponent implements OnInit {

  @Input() movie: MovieInfoDto;

  constructor() { }

  ngOnInit() {
  }

}
