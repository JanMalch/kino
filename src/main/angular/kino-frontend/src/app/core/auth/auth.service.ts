import {Injectable} from '@angular/core';
import {DefaultService} from '@api/api/default.service';
import {Observable, of} from 'rxjs';
import {SignUpDto} from '@api/model/signUpDto';
import {mapTo, mergeMap, pairwise, shareReplay, tap} from 'rxjs/operators';
import {MatSnackBar} from '@angular/material';
import {Router} from '@angular/router';
import {AccountInfoDto} from '@api/model/accountInfoDto';
import {SessionStorageSubject} from './session-storage-subject.rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly token = new SessionStorageSubject('token');

  readonly account$: Observable<AccountInfoDto | null> = this.token$.pipe(
    mergeMap(token => {
      return token === null ? of(null) : this.api.getMyAccount();
    }),
    shareReplay(1)
  );

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
  }

  logIn(email: string, password: string): Observable<void> {
    return this.api.logIn({email, password}).pipe(
      tap(({token}) => this.setToken(token)),
      mapTo(undefined)
    );
  }

  logOut() {
    this.api.logOut().subscribe(() => {
      this.setToken(null);
      this.router.navigateByUrl('/');
    });
  }

  deleteMyAccount() {
    this.api.deleteMyAccount().subscribe(() => {
      this.snackBar.open(`Account erfolgreich gelöscht`, null, {duration: 2500});
      this.setToken(null);
      this.router.navigateByUrl('/');
    });
  }

  signUp(dto: SignUpDto): Observable<void> {
    return this.api.signUp(dto).pipe(
      mergeMap(() => this.logIn(dto.email, dto.password))
    );
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
