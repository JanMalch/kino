import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from "@core/auth/auth.service";
import {map} from "rxjs/operators";
import {IsEmployeePipe} from "@shared/pipes";

@Injectable({
  providedIn: 'root'
})
export class EmployeeGuard implements CanActivate  {

  constructor(private auth: AuthService,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> {
    return this.auth.account$.pipe(
      map(account => {
        const isEmployee = new IsEmployeePipe().transform(account);
        if (!isEmployee) {
          return this.router.parseUrl(`/404`);
        }
        return true;
      })
    );
  }

}
