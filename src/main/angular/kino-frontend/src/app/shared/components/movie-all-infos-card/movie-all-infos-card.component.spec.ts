import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieAllInfosCardComponent } from './movie-all-infos-card.component';

describe('MovieAllInfosCardComponent', () => {
  let component: MovieAllInfosCardComponent;
  let fixture: ComponentFixture<MovieAllInfosCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MovieAllInfosCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieAllInfosCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
