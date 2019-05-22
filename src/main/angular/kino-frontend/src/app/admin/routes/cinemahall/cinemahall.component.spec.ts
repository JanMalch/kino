import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CinemahallComponent} from './cinemahall.component';

describe('CinemahallComponent', () => {
  let component: CinemahallComponent;
  let fixture: ComponentFixture<CinemahallComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CinemahallComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CinemahallComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
