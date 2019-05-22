import {Injectable} from '@angular/core';
import {AuthService} from '@core/auth/auth.service';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {first, mergeMap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {

  constructor(private auth: AuthService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this.auth.token$.pipe(
      first(),
      mergeMap(token => {
        if (token === null) {
          return next.handle(req);
        }
        const clone = req.clone({
          setHeaders: { Authorization: `Bearer ${token}`},
          withCredentials: true
        });
        return next.handle(clone);
      })
    );
  }
}
