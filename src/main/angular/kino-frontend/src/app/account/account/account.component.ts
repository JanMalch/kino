import {Component, OnInit} from '@angular/core';
import {DefaultService} from '@api/api/default.service';
import {Observable} from 'rxjs';
import {SignUpDto} from '@api/model/signUpDto';
import {AuthService} from '@core/auth';
import {startWith} from "rxjs/operators";

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
    this.account$ = this.api.getMyAccount().pipe(
      startWith(undefined)
    );
  }

  deleteAccount() {
    this.api.deleteMyAccount()
      .subscribe(() => this.auth.logOut());
  }

}
