import { TestBed } from '@angular/core/testing';

import { ScientificWorkService } from './scientific-work.service';

describe('ScientificWorkService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ScientificWorkService = TestBed.get(ScientificWorkService);
    expect(service).toBeTruthy();
  });
});
