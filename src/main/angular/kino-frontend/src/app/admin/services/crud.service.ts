import {Observable} from "rxjs";
import {SuccessMessage} from "@api/model/successMessage";
import {Injectable} from "@angular/core";

@Injectable()
export abstract class CrudService<I, O> {

  abstract getForm(): GenericForm

  abstract create(dto: I): Observable<number>;

  abstract readAll(): Observable<O[]>;

  abstract read(id: number): Observable<O>;

  abstract update(id: number, dto: I): Observable<SuccessMessage>;

  abstract delete(id: number): Observable<SuccessMessage>;

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
