import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {AuthService} from '@core/auth';
import {ActivatedRoute, Router} from '@angular/router';
import {catchError, mergeMap} from "rxjs/operators";
import {throwError} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  forward: string;
  loading = false;

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
    if (this.forward === '/login') {
      this.forward = '/movie';
    }
  }

  ngOnInit() {
  }

  login({value}: NgForm) {
    this.loading = true;
    this.auth.logIn(value.email, value.password).pipe(
      catchError(err => {
        this.loading = false;
        return throwError(err);
      }),
      mergeMap(() => this.router.navigateByUrl(this.forward))
    ).subscribe(() => this.loading = false);
  }

}
