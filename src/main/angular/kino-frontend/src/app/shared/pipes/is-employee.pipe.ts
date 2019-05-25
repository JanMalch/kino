import {Pipe, PipeTransform} from '@angular/core';
import {AccountInfoDto} from "@api/model/accountInfoDto";

@Pipe({
  name: 'isEmployee'
})
export class IsEmployeePipe implements PipeTransform {

  transform(account: AccountInfoDto | undefined | null): boolean {
    if (!account) {
      return false;
    }
    return account.role === "ADMIN" || account.role === "MODERATOR";
  }

}
