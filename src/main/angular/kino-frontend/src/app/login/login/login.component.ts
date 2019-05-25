import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {AuthService} from '@core/auth';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  forward: string;

  readonly accounts = ["admin", "moderator", "customer", "customer1"];

  getCredentials(account: string) {
    return {
      value: {
        email: `${account}@account.de`,
        password: account
      }
    } as NgForm
  }

  constructor(private auth: AuthService,
              route: ActivatedRoute,
              private router: Router) {
    this.forward = route.snapshot.queryParamMap.get('forward') || '/movie';
  }

  ngOnInit() {
  }

  login({value}: NgForm) {
    this.auth.logIn(value.email, value.password).subscribe(() => {
      this.router.navigateByUrl(this.forward);
    });
  }

}
