import {Injectable} from '@angular/core';
import {DefaultService} from '@api/api/default.service';
import {BehaviorSubject, Observable} from 'rxjs';
import {SignUpDto} from '@api/model/signUpDto';
import {mapTo, mergeMap, pairwise, tap} from 'rxjs/operators';
import {MatSnackBar} from '@angular/material';
import {Router} from '@angular/router';
import {AccountInfoDto} from "@api/model/accountInfoDto";


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private account = new BehaviorSubject<AccountInfoDto | null>(null);
  private token = new BehaviorSubject<string | null>(null);

  get account$(): Observable<AccountInfoDto | null> {
    return this.account.asObservable();
  }

  get token$(): Observable<string | null> {
    return this.token.asObservable();
  }

  constructor(private api: DefaultService,
              private router: Router,
              private snackBar: MatSnackBar) {
    this.account$.pipe(
      pairwise()
    ).subscribe(([prev, next]) => {
      if (prev === null && next !== null) {
        this.onLogIn(next);
      } else if (prev !== null && next === null) {
        this.onLogOut();
      }
    });

    // this.token$.subscribe(token => localStorage.setItem('token', token));
  }

  logIn(email: string, password: string): Observable<void> {
    return this.api.logIn({email, password}).pipe(
      tap(({token}) => this.token.next(token)),
      mergeMap(() => this.api.getMyAccount()),
      tap(dto => this.account.next(dto)),
      mapTo(undefined)
    );
  }

  logOut() {
    this.api.logOut().subscribe(() => {
      this.account.next(null);
      this.token.next(null);
      this.router.navigateByUrl('/');
    });
  }

  setToken(token: string | null) {
    this.token.next(token);
  }

  private onLogOut() {
    this.snackBar.open(`Erfolgreich ausgeloggt`, null, {duration: 2500});
  }

  private onLogIn(account: SignUpDto) {
    this.snackBar.open(`Willkommen, ${account.firstName} ${account.lastName}!`, null, {duration: 2500});
  }
}
