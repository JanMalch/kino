import {Component, Input, OnInit} from '@angular/core';
import {SignUpDto} from '@api/model/signUpDto';
import {NgForm} from '@angular/forms';
import {DefaultService} from '@api/api/default.service';
import {AuthService} from '@core/auth';

@Component({
  selector: 'app-profile[account]',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  @Input() set account(value: SignUpDto) {
    Object.assign(this, value);
  }

  firstName: string;
  lastName: string;
  password: string;
  email: string;
  birthday: string;

  constructor(private api: DefaultService,
              private auth: AuthService) {
  }

  ngOnInit() {
  }

  updateAccount({value}: NgForm) {
    this.api.editMyAccount(value).subscribe(dto => this.auth.setToken(dto.token));
  }

}
