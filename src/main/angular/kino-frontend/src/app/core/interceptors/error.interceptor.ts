import {Injectable} from '@angular/core';
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
          const message = generateMessage(err.error);
          // no snackbar showing currently
          if (!this.snackBar._openedSnackBarRef) {
            this.snackBar.open(message, 'OK', {duration: 2500});
          }
        }
        return throwError(err);
      })
    );
  }
}

export function isProblem(err: Error): boolean {
  return err instanceof HttpErrorResponse &&
    err.headers.get('content-type').startsWith('application/problem');
}

export function generateMessage({title, detail, instance}: { title?: string; detail?: string; instance?: string }): string {
  if (!title) {
    return 'Unbekannter Serverfehler aufgetreten';
  }
  let output = title;
  if (!output.endsWith('.')) {
    output += '. ';
  }
  if (!!detail) {
    output += detail;
    if (!output.endsWith('.')) {
      output += '. ';
    }
  }
  if (!!instance) {
    const regex = /.+time=(\d+)/gm;
    let m;

    while ((m = regex.exec(instance)) !== null) {
      // This is necessary to avoid infinite loops with zero-width matches
      if (m.index === regex.lastIndex) {
        regex.lastIndex++;
      }

      const [, time] = m;
      output += `Code: ${time}`;
    }
  }

  return output.trim();
}
