import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PricecategoryComponent} from './pricecategory.component';

describe('PricecategoryComponent', () => {
  let component: PricecategoryComponent;
  let fixture: ComponentFixture<PricecategoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PricecategoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PricecategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
