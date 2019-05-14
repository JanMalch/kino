import {Component, OnInit} from '@angular/core';
import {DefaultService} from '@api/api/default.service';
import {Observable} from 'rxjs';
import {SignUpDto} from '@api/model/signUpDto';
import {ReservationInfoDto} from '@api/model/reservationInfoDto';
import {AuthService} from '@core/auth';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

  account$: Observable<SignUpDto>;

  constructor(private api: DefaultService,
              private auth: AuthService) {
  }

  ngOnInit() {
    this.account$ = this.api.getMyAccount();
  }

  deleteAccount() {
    this.api.deleteMyAccount()
      .subscribe(() => this.auth.logOut());
  }

}
