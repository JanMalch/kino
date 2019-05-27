import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MaterialModule} from '@material/material.module';
import {HttpClientModule} from '@angular/common/http';
import {HallOverviewComponent, MovieAllInfosCardComponent, MovieCardComponent} from '@shared/components';
import {HasMinRolePipe, IsEmployeePipe, ParseDatePipe, SeatColorPipe, SeatMatrixPipe} from '@shared/pipes';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    MovieCardComponent,
    HallOverviewComponent,
    SeatMatrixPipe,
    SeatColorPipe,
    MovieAllInfosCardComponent,
    ParseDatePipe,
    HasMinRolePipe,
    IsEmployeePipe
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
    ParseDatePipe,
    HasMinRolePipe,
    IsEmployeePipe
  ]
})
export class SharedModule {
}
