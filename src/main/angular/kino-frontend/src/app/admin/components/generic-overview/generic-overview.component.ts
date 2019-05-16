import {Component, ContentChild, Input, OnInit, TemplateRef, ViewEncapsulation} from '@angular/core';
import {Observable} from "rxjs";
import {CrudService} from "@admin/services";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material";
import {shareReplay, startWith} from "rxjs/operators";
import {SuccessMessage} from "@api/model/successMessage";
import {EntityDirective} from "@admin/directives";

@Component({
  selector: 'app-generic-overview',
  templateUrl: './generic-overview.component.html',
  styleUrls: ['./generic-overview.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class GenericOverviewComponent<T, O> implements OnInit {

  @Input() onCreateRedirect: string;
  @Input() entityName: string;
  @Input() entityNamePlural: string;

  @ContentChild(EntityDirective, { read: TemplateRef}) entityTemplate : TemplateRef<HTMLElement>;

  selected: T = null;
  entities$: Observable<T[]>;

  constructor(private crud: CrudService<O, T>,
              private router: Router,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.entities$ = this.crud.readAll().pipe(
      startWith(null),
      shareReplay(1)
    );
  }

  onCreate(result: number) {
    this.selected = null;

    const redirectAfterwards = this.onCreateRedirect !== undefined;

    const ref = this.snackBar.open("Erfolreich angelegt",
      redirectAfterwards ? "OPEN" : "OK",
      {
        duration: 2500
      });

    if (redirectAfterwards) {
      ref.onAction().subscribe(() => {
        const url = this.onCreateRedirect.replace(":id", result.toString(10));
        this.router.navigateByUrl(url);
      });
    }
  }

  onUpdate(result: SuccessMessage) {
    this.selected = null;
    this.snackBar.open(result.message, "OK", {
      duration: 2500
    });
  }

  onDelete(result: SuccessMessage) {
    this.selected = null;
    this.snackBar.open(result.message, "OK", {
      duration: 2500
    });
  }

  setSelect(dto: T) {
    this.selected = dto;
  }

  resolveIcon(role: string): string {
    return role === "ADMIN" || role === "MODERATOR" ? "account-badge" : "account-circle";
  }

}
