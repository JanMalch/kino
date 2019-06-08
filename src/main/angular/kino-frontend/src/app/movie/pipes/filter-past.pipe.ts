import {Pipe, PipeTransform} from '@angular/core';
import {PresentationDto} from "@api/model/presentationDto";
import {ParseDatePipe} from "@shared/pipes";

@Pipe({
  name: 'filterPast'
})
export class FilterPastPipe implements PipeTransform {

  private readonly parseDatePipe = new ParseDatePipe();

  transform(value: PresentationDto[]): PresentationDto[] {
    const now = Date.now();
    return value.filter(presentation => {
      const parsed = this.parseDatePipe.transform(presentation.date);
      return parsed.getTime() >= now;
    });
  }

}
