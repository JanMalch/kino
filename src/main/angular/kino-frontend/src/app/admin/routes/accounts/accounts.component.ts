import {Component, Injectable, OnInit} from '@angular/core';
import {CrudService, GenericForm} from "@admin/services";
import {AccountDto} from "@api/model/accountDto";
import {Observable} from "rxjs";
import {DefaultService} from "@api/api/default.service";
import {SuccessMessage} from "@api/model/successMessage";
import {AccountInfoDto} from "@api/model/accountInfoDto";
import {shareReplay} from "rxjs/operators";

@Injectable()
export class AccountCrudService implements CrudService<AccountDto, AccountInfoDto> {

  constructor(private api: DefaultService) {
  }

  create(dto: AccountDto): Observable<number> {
    return undefined;
  }

  delete(id: number): Observable<SuccessMessage> {
    return this.api.deleteAccount(id);
  }

  getForm(): GenericForm {
    return {
      firstName: {label: "Vorname", name: "firstName", type: "text", validation: {required: true}},
      lastName: {label: "Nachname", name: "lastName", type: "text", validation: {required: true}},
      password: {label: "Passwort", name: "password", type: "password", validation: {required: true}},
      email: {label: "Email", name: "email", type: "email", validation: {required: true}},
      birthday: {label: "Geburtstag", name: "birthday", type: "text", validation: {required: true}},
      role: {
        label: "Rolle", name: "role", type: "select", options: [
          {
            value: "ADMIN",
            label: "Admin"
          },
          {
            value: "MODERATOR",
            label: "Moderator"
          },{
            value: "CUSTOMER",
            label: "Customer"
          }
        ], validation: {required: true}
      }
    };
  }

  read(id: number): Observable<AccountInfoDto> {
    return this.api.getAccountById(id);
  }

  readAll(): Observable<AccountInfoDto[]> {
    return this.api.getAllAccounts();
  }

  update(id: number, dto: AccountDto): Observable<SuccessMessage> {
    return undefined;
  }

}

@Component({
  selector: 'app-reservations',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.scss'],
  providers: [AccountCrudService]
})
export class AccountsComponent implements OnInit {

  selected: AccountInfoDto = null;
  schema: GenericForm = this.accountCrudService.getForm();
  entities$: Observable<AccountInfoDto[]>;

  constructor(private accountCrudService: AccountCrudService) {
  }

  ngOnInit() {
    this.entities$ = this.accountCrudService.readAll().pipe(
      shareReplay(1)
    );
  }

  onCreate(value: any) {
    this.accountCrudService.create(value).subscribe(console.log);
  }

  onUpdate(value: any) {
    this.accountCrudService.update(this.selected.id, value).subscribe(console.log);
  }

  onDelete() {
    this.accountCrudService.delete(this.selected.id).subscribe(console.log);
  }

  resolveIcon(role: string): string {
    return role === "ADMIN" || role === "MODERATOR" ? "account-badge" : "account-circle";
  }

  setSelect(dto: AccountInfoDto) {
    this.selected = dto;
  }
}
