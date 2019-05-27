import {Injectable, OnDestroy} from '@angular/core';
import {Observable} from "rxjs";
import {DefaultService} from "@api/api/default.service";
import {untilDestroyed} from "ngx-take-until-destroy";
import {map, shareReplay} from "rxjs/operators";
import {PresentationWithSeatsDto} from "@api/model/presentationWithSeatsDto";

@Injectable({
  providedIn: 'root'
})
export class PresentationService implements OnDestroy {

  private presentations$: Observable<PresentationWithSeatsDto[]>;

  constructor(private api: DefaultService) {
    this.refresh();
  }

  refresh() {
    this.presentations$ = this.api.getAllPresentations().pipe(
      untilDestroyed(this),
      shareReplay(1)
    );
  }

  getPresentation(id: number): Observable<PresentationWithSeatsDto> {
    return this.presentations$.pipe(
      map(presentations => presentations.find(p => p.id === id))
    );
  }

  getAllPresentations(): Observable<PresentationWithSeatsDto[]> {
    return this.presentations$;
  }

  ngOnDestroy(): void {
  }

}
