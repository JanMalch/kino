import {Component} from '@angular/core';
import {AuthService} from '@core/auth';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent {

  constructor(public auth: AuthService) {
  }

  deleteAccount() {
    this.auth.deleteMyAccount();
  }

}
