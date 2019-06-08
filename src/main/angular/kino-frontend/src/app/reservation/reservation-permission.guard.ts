import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable, of, throwError} from 'rxjs';
import {AuthService} from '@core/auth';
import {filter, map, mergeMap} from 'rxjs/operators';
import {ReservationService} from '@core/services';

@Injectable({
  providedIn: 'root'
})
export class ReservationPermissionGuard implements CanActivate {

  constructor(private authService: AuthService,
              private reservationService: ReservationService,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> {
    return this.authService.token$.pipe(
      mergeMap(token => {
        if (token === null) {
          return of(null);
        } else {
          return this.authService.account$.pipe(
            filter(Boolean)
          );
        }
      }),
      mergeMap(account => {
        if (account === null) {
          return throwError('ReservationPermissionGuard should run after AuthGuard to ensure login');
        }
        if (account.role === 'MODERATOR' || account.role === 'ADMIN') {
          return of(this.router.parseUrl('/admin'));
        }
        const id = parseInt(route.paramMap.get('id'), 10);
        return this.reservationService.getMyReservation(id).pipe(
          map(result => {
            if (!result) {
              return this.router.parseUrl('/404');
            } else {
              return true;
            }
          })
        );
      })
    );
  }

}
