import {Injectable, isDevMode} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {MatSnackBar} from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private snackBar: MatSnackBar) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError(err => {
        if (isProblem(err)) {
          if (err.error.status === 404) {
            return throwError(err);
          }
          let {title, detail} = err.error;
          title = !!title ? title : 'Unbekannter Serverfehler aufgetreten';
          detail = !!detail ? detail + '.' : '';
          this.snackBar.open(`${title}. ${detail}`.trim(), 'OK', {duration: 5000});
        }
        return throwError(err);
      })
    );
  }
}

function isProblem(err: Error): boolean {
  if (!(err instanceof HttpErrorResponse) || !('error' in err)) {
    return false;
  }
  return 'type' in err.error;
}
