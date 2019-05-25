import {HasMinRolePipe} from './has-role.pipe';

describe('HasMinRolePipe', () => {
  it('create an instance', () => {
    const pipe = new HasMinRolePipe();
    expect(pipe).toBeTruthy();
  });
});
