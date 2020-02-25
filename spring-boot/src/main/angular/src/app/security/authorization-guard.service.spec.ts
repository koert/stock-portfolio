import { TestBed, async, inject } from '@angular/core/testing';

import { AuthorizationGuard } from './authorization-guard.service';

describe('AuthorizationGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AuthorizationGuard]
    });
  });

  it('should ...', inject([AuthorizationGuard], (guard: AuthorizationGuard) => {
    expect(guard).toBeTruthy();
  }));
});
