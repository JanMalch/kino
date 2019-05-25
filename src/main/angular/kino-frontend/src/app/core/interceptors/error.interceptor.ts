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
          this.snackBar.open(message, 'OK', {duration: 5000});
        }
        return throwError(err);
      })
    );
  }
}

export function isProblem(err: Error): boolean {
  if (!(err instanceof HttpErrorResponse) || !('error' in err)) {
    return false;
  }
  return 'type' in err.error;
}

export function generateMessage({title, detail}: { title?: string; detail?: string}): string {
  if (!title) {
    return 'Unbekannter Serverfehler aufgetreten';
  }
  let output = title;
  if (!output.endsWith(".")) {
    output += ". ";
  }
  if (!!detail) {
    output += detail;
    if (!output.endsWith(".")) {
      output += ".";
    }
  }

  return output.trim();
}
