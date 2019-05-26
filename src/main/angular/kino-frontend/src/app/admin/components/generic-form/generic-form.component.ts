import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CrudService} from "@admin/services";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SuccessMessage} from "@api/model/successMessage";

@Component({
  selector: 'app-generic-form',
  templateUrl: './generic-form.component.html',
  styleUrls: ['./generic-form.component.scss']
})
export class GenericFormComponent implements OnInit {

  get data(): any {
    return this._data;
  }

  @Input() set data(value: any) {
    this._data = value;
    if (!!this.form) {
      if (value === null) {
        this.form.reset(null);
      } else {
        this.form.patchValue(value);
        this.updateDisabled = this.crud.isDisabled('UPDATE');
      }
    }
  }

  private _data: any;
  updateDisabled: boolean = false;

  @Output() create = new EventEmitter<number>();
  @Output() update = new EventEmitter<SuccessMessage>();
  @Output() delete = new EventEmitter<SuccessMessage>();

  objectProps: any[];
  form: FormGroup;

  constructor(private crud: CrudService<any, any>) {
  }

  clearForm() {
    this.form.reset();
    this.form.setErrors(null);
    this.form.markAsPristine();
    this.form.markAsUntouched();
  }

  ngOnInit() {
    const data = this._data || {};
    const schema = this.crud.getForm();
    // remap the API to be suitable for iterating over it
    this.objectProps =
      Object.keys(schema)
        .map(prop => {
          return Object.assign({}, {key: prop}, schema[prop]);
        });

    // setup the form
    const formGroup = {};
    for (let prop of Object.keys(schema)) {
      const ctrl = new FormControl(data[prop] || '',
        this.mapValidators(schema[prop].validation));
      if(data[prop] && data[prop].type === "array") {
        ctrl.disable();
      }
      formGroup[prop] = ctrl;
    }

    this.form = new FormGroup(formGroup);
  }

  onSubmit(formValue: any) {
    if (!!this._data) {
      console.log("Updating ...", formValue);
      this.crud.update(this.data.id, formValue)
        .subscribe(result => this.update.emit(result));
    } else {
      console.log("Creating ...", formValue);
      this.crud.create(formValue)
        .subscribe(result => this.create.emit(result));
    }
  }

  onDelete() {
    this.crud.delete(this.data.id)
      .subscribe(result => this.delete.emit(result));
  }

  private mapValidators(validators) {
    const formValidators = [];

    if (validators) {
      for (const validation of Object.keys(validators)) {
        if (validation === 'required') {
          formValidators.push(Validators.required);
        } else if (validation === 'min') {
          formValidators.push(Validators.min(validators[validation]));
        }
      }
    }

    return formValidators;
  }

}
