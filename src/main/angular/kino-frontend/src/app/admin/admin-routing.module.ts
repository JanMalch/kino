import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard, EmployeeGuard} from "@core/auth";
import {DashboardComponent} from "@admin/routes";

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: DashboardComponent,
    canActivate: [AuthGuard, EmployeeGuard]
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: '/404'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
