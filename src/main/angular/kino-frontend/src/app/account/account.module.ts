import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AccountRoutingModule} from './account-routing.module';
import {AccountComponent} from './account/account.component';
import {ProfileComponent} from './profile/profile.component';
import {SharedModule} from '@shared/shared.module';

@NgModule({
  declarations: [AccountComponent, ProfileComponent],
  imports: [
    CommonModule,
    AccountRoutingModule,
    SharedModule
  ]
})
export class AccountModule { }
