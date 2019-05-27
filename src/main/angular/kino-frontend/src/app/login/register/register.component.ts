import {Component, OnInit} from '@angular/core';
import {AuthService} from "@core/auth";
import {ActivatedRoute, Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {mergeMap} from "rxjs/operators";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  loading = false;

  constructor(private auth: AuthService,
              route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
  }

  register({value}: NgForm) {
    this.loading = true;
    this.auth.signUp({...value}).pipe(
      mergeMap(() => this.router.navigateByUrl("/"))
    ).subscribe(() => this.loading = false);
  }

}
