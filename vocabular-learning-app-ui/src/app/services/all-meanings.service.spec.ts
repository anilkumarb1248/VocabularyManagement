import { TestBed } from '@angular/core/testing';

import { AllMeaningsService } from './all-meanings.service';

describe('AllMeaningsService', () => {
  let service: AllMeaningsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AllMeaningsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
