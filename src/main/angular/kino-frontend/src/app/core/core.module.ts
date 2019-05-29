import {LOCALE_ID, NgModule} from '@angular/core';
import {CommonModule, registerLocaleData} from '@angular/common';

import {CoreRoutingModule} from './core-routing.module';
import {SkeletonComponent} from './skeleton/skeleton.component';
import {SharedModule} from '@shared/shared.module';
import {ApiModule} from '@api/api.module';
import {Configuration, ConfigurationParameters} from '@api/configuration';
import localeDe from '@angular/common/locales/de';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {AuthInterceptor} from '@core/auth';
import {NotFoundComponent} from './not-found/not-found.component';
import {ErrorInterceptor} from '@core/interceptors';
import {RouterModule} from "@angular/router";
import {environment} from "../../environments/environment";

registerLocaleData(localeDe);

export function apiConfigFactory(): Configuration {
  const params: ConfigurationParameters = {
    // set configuration parameters here.
    basePath: environment.api
  };
  return new Configuration(params);
}

@NgModule({
  declarations: [SkeletonComponent, NotFoundComponent],
  imports: [
    CommonModule,
    CoreRoutingModule,
    SharedModule,
    ApiModule.forRoot(apiConfigFactory)
  ],
  exports: [SkeletonComponent, RouterModule],
  providers: [
    {
      provide: LOCALE_ID, useFactory: () => navigator.language || 'de-DE'
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    }
  ]
})
export class CoreModule {
}
