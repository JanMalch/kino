import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {LoginRoutingModule} from './login-routing.module';
import {LoginComponent} from './login/login.component';
import {SharedModule} from '@shared/shared.module';
import {RegisterComponent} from './register/register.component';
import {LoginOrRegisterComponent} from './login-or-register/login-or-register.component';

@NgModule({
  declarations: [LoginComponent, RegisterComponent, LoginOrRegisterComponent],
  imports: [
    CommonModule,
    LoginRoutingModule,
    SharedModule
  ]
})
export class LoginModule { }
