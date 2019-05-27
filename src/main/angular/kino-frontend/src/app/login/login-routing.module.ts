import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginOrRegisterComponent} from "@login/login-or-register/login-or-register.component";

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: LoginOrRegisterComponent
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoginRoutingModule { }
