import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'parseDate'
})
export class ParseDatePipe implements PipeTransform {

  transform(value: string | number | Date, args?: any): Date {
    if (value instanceof Date) {
      return value;
    }
    if (typeof value === 'number') {
      return new Date(value);
    }

    // 31 May 2019 00:00:00 GMT
    const regex = new RegExp(/(\d{1,2}) (\w+) (\d{4}) (\d{2}):(\d{2}):(\d{2}) (\w+)/g);
    if (regex.test(value)) {
      return new Date(value);
    }
    const [year, month, ...remainder] = value.split(/-|:|T|Z|\./g)
      .map(x => parseInt(x, 10))
      .filter(x => !isNaN(x));
    return new Date(Date.UTC(year, month - 1, ...remainder));
  }

}
