import {BehaviorSubject} from 'rxjs';

export class SessionStorageSubject extends BehaviorSubject<string | null> {

  constructor(private readonly key: string) {
    super(sessionStorage.getItem(key) || null);
  }

  next(value: string | null): void {
    if (value === null) {
      sessionStorage.removeItem(this.key);
    } else {
      sessionStorage.setItem(this.key, value);
    }
    super.next(value);
  }
}
