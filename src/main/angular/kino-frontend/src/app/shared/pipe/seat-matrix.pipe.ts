import { Pipe, PipeTransform } from '@angular/core';
import {SeatForPresentationDto} from '@api/model/seatForPresentationDto';

@Pipe({
  name: 'seatMatrix'
})
export class SeatMatrixPipe implements PipeTransform {

  transform(value: SeatForPresentationDto[] = [], args?: any): SeatForPresentationDto[][] {
    return Object.entries(value.reduce((acc, curr) => {
      if (!(curr.row in acc)) {
        acc[curr.row] = [];
      }
      acc[curr.row].push(curr);
      return acc;
    }, {}))
      .sort(([keyA], [keyB]) => keyA.toLowerCase().localeCompare(keyB.toLowerCase()))
      .map(([, val]) => val);
  }

}
