import {Pipe, PipeTransform} from '@angular/core';
import {SeatForPresentationDto} from '@api/model/seatForPresentationDto';
import {ThemePalette} from '@angular/material';
import {Selectable} from '@shared/components';

@Pipe({
  name: 'seatColor'
})
export class SeatColorPipe implements PipeTransform {

  transform(value: SeatForPresentationDto & Selectable): ThemePalette | 'default' {
    if (value.selected) {
      return 'primary';
    }
    if (value.taken) {
      return 'warn';
    }
    // returning undefined for no color does not work
    return 'default';
  }

}
