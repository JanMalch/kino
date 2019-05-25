import {Component, Injectable} from '@angular/core';
import {CrudService, GenericForm} from "@admin/services";
import {Observable} from "rxjs";
import {DefaultService} from "@api/api/default.service";
import {SuccessMessage} from "@api/model/successMessage";
import {AccountInfoDto} from "@api/model/accountInfoDto";
import {map} from "rxjs/operators";

@Injectable()
export class AccountCrudService implements CrudService<AccountInfoDto, AccountInfoDto> {

  constructor(private api: DefaultService) {
  }

  create(dto: AccountInfoDto): Observable<number> {
    const {role, id, ...signUpDto} = dto;
    return this.api.signUp(signUpDto);
  }

  delete(id: number): Observable<SuccessMessage> {
    return this.api.deleteAccount(id);
  }

  getForm(): GenericForm {
    return {
      firstName: {label: "Vorname", name: "firstName", type: "text", validation: {required: true, min: 1}},
      lastName: {label: "Nachname", name: "lastName", type: "text", validation: {required: true, min: 1}},
      password: {label: "Passwort", name: "password", type: "password"},
      email: {label: "Email", name: "email", type: "email", validation: {required: true}},
      birthday: {label: "Geburtstag", name: "birthday", type: "text", validation: {required: true, min: 10}},
      role: {
        label: "Rolle", name: "role", type: "select", options: [
          {
            value: "ADMIN",
            label: "Admin"
          },
          {
            value: "MODERATOR",
            label: "Moderator"
          }, {
            value: "CUSTOMER",
            label: "Customer"
          }
        ]
      }
    };
  }

  read(id: number): Observable<AccountInfoDto> {
    return this.api.getAccountById(id);
  }

  readAll(): Observable<AccountInfoDto[]> {
    return this.api.getAllAccounts();
  }

  update(id: number, dto: AccountInfoDto): Observable<SuccessMessage> {
    return this.api.editAccountById(id, dto).pipe(
      map(() => ({
        message: "Account erfolgreich aktualisiert."
      }))
    );
  }

}

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.scss'],
  providers: [{provide: CrudService, useClass: AccountCrudService}]
})
export class AccountsComponent {

  constructor(private crud: CrudService<AccountInfoDto, AccountInfoDto>) {
  }

  resolveIcon(role: string): string {
    return role === "ADMIN" || role === "MODERATOR" ? "account-badge" : "account-circle";
  }

}
