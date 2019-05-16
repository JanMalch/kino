import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AdminRoutingModule} from './admin-routing.module';
import {GenericFormComponent} from './components/generic-form/generic-form.component';
import {SharedModule} from "@shared/shared.module";
import {AccountsComponent} from './routes/accounts/accounts.component';
import {DashboardComponent} from './routes/dashboard/dashboard.component';
import {GenericOverviewComponent} from './components/generic-overview/generic-overview.component';
import {EntityDirective} from './directives/entity.directive';
import {MoviesComponent} from './routes/movies/movies.component';
import {ReservationsComponent} from './routes/reservations/reservations.component';

@NgModule({
  declarations: [GenericFormComponent, AccountsComponent, DashboardComponent, GenericOverviewComponent, EntityDirective, MoviesComponent, ReservationsComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule
  ]
})
export class AdminModule { }
