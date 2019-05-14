import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable, of, throwError} from 'rxjs';
import {DefaultService} from '@api/api/default.service';
import {catchError, tap} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';
import {MovieDto} from '@api/model/movieDto';

@Injectable()
export class MovieResolver implements Resolve<MovieDto>  {

  constructor(private api: DefaultService,
              private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MovieDto>  {
    return this.api.getMovie(parseInt(route.paramMap.get('id'), 10)).pipe(
      catchError(err => {
        if (err instanceof  HttpErrorResponse && err.status === 404) {
          this.router.navigateByUrl('/404');
          return of(null);
        }
        return throwError(err);
      })
    );
  }

}
