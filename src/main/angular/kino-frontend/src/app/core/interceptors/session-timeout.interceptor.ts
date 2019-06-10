import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class SessionTimeoutInterceptor implements HttpInterceptor {


  constructor(private router: Router,
              private snackBar: MatSnackBar) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError(err => {
        if (err instanceof HttpErrorResponse && err.status === 401) {
          this.snackBar.dismiss();
          this.snackBar.open('Your session has expired. Please login to continue', null, {
            duration: 2500
          });
          this.router.navigateByUrl(`/login?forward=${this.router.url}`);
        }
        return throwError(err);
      })
    );
  }
}
