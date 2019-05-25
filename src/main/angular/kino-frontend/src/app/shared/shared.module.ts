import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MaterialModule} from '@material/material.module';
import {HttpClientModule} from '@angular/common/http';
import {HallOverviewComponent, MovieAllInfosCardComponent, MovieCardComponent} from '@shared/components';
import {ParseDatePipe, SeatColorPipe, SeatMatrixPipe} from '@shared/pipe';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    MovieCardComponent,
    HallOverviewComponent,
    SeatMatrixPipe,
    SeatColorPipe,
    MovieAllInfosCardComponent,
    ParseDatePipe
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MovieCardComponent,
    HallOverviewComponent,
    MovieAllInfosCardComponent,
    ParseDatePipe
  ]
})
export class SharedModule {
}
