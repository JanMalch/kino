import {Injectable, Injector} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {AuthService} from '@core/auth';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptor implements HttpInterceptor {

  private get authService(): AuthService {
    // angular bug?
    return this.injector.get(AuthService);
  }

  constructor(/*private authService: AuthService,*/
              private injector: Injector) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      tap(res => {
        if (res instanceof HttpResponse && res.headers.has('token')) {
          const refreshedToken = res.headers.get('token');
          if (!!this.authService) {
            this.authService.setToken(refreshedToken);
          }
        }
      })
    );
  }
}
