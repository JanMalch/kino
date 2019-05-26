import {Observable} from "rxjs";
import {SuccessMessage} from "@api/model/successMessage";
import {Injectable} from "@angular/core";

export type CrudAction = 'CREATE' | 'READ' | 'UPDATE' | 'DELETE' | 'READ_ALL';

@Injectable()
export abstract class CrudService<I, O> {
  abstract getForm(): GenericForm

  abstract create(dto: I): Observable<number>;

  abstract readAll(): Observable<O[]>;

  abstract read(id: number): Observable<O>;

  abstract update(id: number, dto: I): Observable<SuccessMessage>;

  abstract delete(id: number): Observable<SuccessMessage>;


  isDisabled(checkFor: CrudAction): boolean {
    return false;
  }

}

export interface GenericForm {
  [name: string]: FormField;
}

export interface FormField {
  name: string;
  label: string;
  type: "text" | "number" | "password" | "email" | "select" | "array";
  options?: { value: any, label: string}[];
  validation?: {
    required?: boolean;
    min?: number;
  }
}
