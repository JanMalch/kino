import {Pipe, PipeTransform} from '@angular/core';
import {AccountInfoDto} from "@api/model/accountInfoDto";

@Pipe({
  name: 'hasMinRole'
})
export class HasMinRolePipe implements PipeTransform {

  transform(account: AccountInfoDto, minRole: AccountInfoDto.RoleEnum): boolean {
    const roleRank = roleHierarchy[account.role];
    const requiredRole = roleHierarchy[minRole];
    return roleRank >= requiredRole;
  }

}

const roleHierarchy = {
  GUEST: 0,
  CUSTOMER: 10,
  MODERATOR: 20,
  ADMIN: 30
};
