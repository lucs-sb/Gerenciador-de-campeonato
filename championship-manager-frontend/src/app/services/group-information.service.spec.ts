import { TestBed } from '@angular/core/testing';

import { GroupInformationService } from './group-information.service';

describe('GroupInformationService', () => {
  let service: GroupInformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GroupInformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
