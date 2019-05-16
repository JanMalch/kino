import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AdminRoutingModule} from './admin-routing.module';
import {GenericFormComponent} from './components/generic-form/generic-form.component';
import {SharedModule} from "@shared/shared.module";
import {AccountsComponent} from './routes/accounts/accounts.component';
import {DashboardComponent} from './routes/dashboard/dashboard.component';

@NgModule({
  declarations: [GenericFormComponent, AccountsComponent, DashboardComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule
  ]
})
export class AdminModule { }
