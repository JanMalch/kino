import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GenericForm} from "@admin/services";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-generic-form[schema]',
  templateUrl: './generic-form.component.html',
  styleUrls: ['./generic-form.component.scss']
})
export class GenericFormComponent implements OnInit {

  @Input() schema: GenericForm;
  @Input() data: any;

  @Output() create = new EventEmitter();
  @Output() update = new EventEmitter();
  @Output() delete = new EventEmitter<void>();

  objectProps: any[];
  form: FormGroup;


  ngOnInit() {
    this.data = this.data || {};
    // remap the API to be suitable for iterating over it
    this.objectProps =
      Object.keys(this.schema)
        .map(prop => {
          return Object.assign({}, {key: prop}, this.schema[prop]);
        });

    // setup the form
    const formGroup = {};
    for (let prop of Object.keys(this.schema)) {
      formGroup[prop] = new FormControl(this.data[prop] || '',
        this.mapValidators(this.schema[prop].validation));
    }

    this.form = new FormGroup(formGroup);
  }

  onSubmit(formValue: any) {
    if (this.data !== undefined) {
      console.log("Updating ...", formValue);
      this.update.emit(formValue);
    } else {
      console.log("Creating ...", formValue);
      this.create.emit(formValue);
    }
  }

  onDelete() {
    this.delete.emit();
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
