import {Component, Injectable} from '@angular/core';
import {CrudService, GenericForm} from "@admin/services";
import {PriceCategoryBaseDto} from "@api/model/priceCategoryBaseDto";
import {DefaultService} from "@api/api/default.service";
import {Observable} from "rxjs";
import {SuccessMessage} from "@api/model/successMessage";
import {PriceCategoryDto} from "@api/model/priceCategoryDto";


@Injectable()
export class PriceCategoryCrudService implements CrudService<PriceCategoryBaseDto, PriceCategoryDto> {

  constructor(private api: DefaultService) {
  }

  create(dto: PriceCategoryBaseDto): Observable<number> {
    return this.api.createPriceCategory(dto);
  }

  delete(id: number): Observable<SuccessMessage> {
    return this.api.deletePriceCategory(id);
  }

  getForm(): GenericForm {
    return {
      name: {label: "Bezeichnung", name: "name", type: "text", validation: {required: true, min: 1}},
      regularPrice: {label: "Regulärer Preis", name: "regularPrice", type: "number", validation: {required: true}},
      reducedPrice: {label: "Ermäßigter Preis", name: "reducedPrice", type: "number", validation: {required: true}}
    };
  }

  read(id: number): Observable<PriceCategoryDto> {
    return this.api.getPriceCategory(id);
  }

  readAll(): Observable<PriceCategoryDto[]> {
    return this.api.getAllPriceCategories();
  }

  update(id: number, dto: PriceCategoryBaseDto): Observable<SuccessMessage> {
    return this.api.updatePriceCategory(id, dto);
  }

}


@Component({
  selector: 'app-pricecategory',
  templateUrl: './pricecategory.component.html',
  styleUrls: ['./pricecategory.component.scss'],
  providers: [{provide: CrudService, useClass: PriceCategoryCrudService}]
})
export class PricecategoryComponent {


  constructor(private crud: CrudService<PriceCategoryBaseDto, PriceCategoryDto>) {
  }

  resolveIcon(role: string): string {
    return role === "ADMIN" || role === "MODERATOR" ? "account-badge" : "account-circle";
  }

}
