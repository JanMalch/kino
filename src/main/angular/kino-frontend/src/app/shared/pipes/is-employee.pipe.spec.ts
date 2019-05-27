import {IsEmployeePipe} from './is-employee.pipe';

describe('IsEmployeePipe', () => {
  it('create an instance', () => {
    const pipe = new IsEmployeePipe();
    expect(pipe).toBeTruthy();
  });
});
