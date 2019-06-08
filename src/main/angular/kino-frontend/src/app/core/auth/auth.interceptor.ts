import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = sessionStorage.getItem('token');

    if (token === null) {
      return next.handle(req);
    }
    const clone = req.clone({
      setHeaders: { Authorization: `Bearer ${token}`},
      withCredentials: true
    });
    return next.handle(clone);
  }
}
