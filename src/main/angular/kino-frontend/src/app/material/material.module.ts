import {NgModule} from '@angular/core';
import {
  MatButtonModule,
  MatCardModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDividerModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatIconRegistry,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSidenavModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule
} from '@angular/material';
import {DomSanitizer} from '@angular/platform-browser';
import {LayoutModule} from '@angular/cdk/layout';

@NgModule({
  exports: [
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatCardModule,
    MatSnackBarModule,
    MatGridListModule,
    MatMenuModule,
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatTabsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatChipsModule,
    MatExpansionModule,
    MatDividerModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatSlideToggleModule,
    MatFormFieldModule,
    MatProgressBarModule,
    MatSelectModule
  ]
})
export class MaterialModule {

  constructor(matIconRegistry: MatIconRegistry, domSanitizer: DomSanitizer) {
    const secureUrl = domSanitizer.bypassSecurityTrustResourceUrl('./assets/mdi.svg');
    matIconRegistry.addSvgIconSet(secureUrl);
  }

}
